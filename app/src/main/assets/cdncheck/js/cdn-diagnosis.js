define('liwushuo/settings/cdn-diagnosis', function(require, exports, module){ var $ = require("web/components/jquery/jquery.js");

var _ua = navigator.userAgent.toLowerCase();
if (/(gifttalk)/i.test(_ua)) {
  $('#copy-btn')
    .css({"display": "block"})
    .show();
}

$("#check-network-again").on("click", function(e) {
  location.replace(location.protocol + "//" + location.host + location.pathname);
});

var getParam = function(name, url) {
  if (!url) url = window.location.href;
  if (typeof name !== "string") {
    return;
  }
  name = name.replace(/[\[\]]/g, "\\$&");
  var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
    results = regex.exec(url);
  if (!results) return null;
  if (!results[2]) return '';
  return decodeURIComponent(results[2].replace(/\+/g, " "));
};

if (getParam("a_sp_1") || getParam("client_ip")) {
  var _arr = ["user_agent", "os_info", "browser_info", "flash_version", "zhalang_home_page", "cookie_enable", "local_storage", "pic_cdn_info", "lws_api", "lws_home_page", "JS_VERSION", "cdn_info", "lws_detail_page", "taobao_home_page", "alibaba_home_page", "qq_home_page", "local_target_ip", "local_dns_info"];
  for (var i = 0; i < _arr.length; i++) {
    eval('document.getElementById("' + _arr[i] + '").innerHTML = getParam("' + _arr[i] + '")');
  }
  return;
}

/*
 * Copyright (c) 2011, Yahoo! Inc.  All rights reserved.
 * Copyright (c) 2012, Log-Normal, Inc.  All rights reserved.
 * Copyright (c) 2013, SOASTA, Inc. All rights reserved.
 * Copyrights licensed under the BSD License. See the accompanying LICENSE.txt file for terms.
 */

/**
\file boomerang.js
boomerang measures various performance characteristics of your user's browsing
experience and beacons it back to your server.

\details
To use this you'll need a web site, lots of users and the ability to do
something with the data you collect.  How you collect the data is up to
you, but we have a few ideas.
*/

// Measure the time the script started
// This has to be global so that we don't wait for the entire
// BOOMR function to download and execute before measuring the
// time.  We also declare it without `var` so that we can later
// `delete` it.  This is the only way that works on Internet Explorer
BOOMR_start = new Date().getTime();

// beaconing section
// the parameter is the window
(function(w) {

  // This is the only block where we use document without the w. qualifier
  if (w.parent !== w && document.getElementById('boomr-if-as') && document.getElementById('boomr-if-as').nodeName.toLowerCase() === 'script') {
    w = w.parent;
  }

  var impl, boomr, k, d = w.document;

  // Short namespace because I don't want to keep typing BOOMERANG
  if (w.BOOMR === undefined) {
    w.BOOMR = {};
  }
  BOOMR = w.BOOMR;
  // don't allow this code to be included twice
  if (BOOMR.version) {
    return;
  }

  BOOMR.version = "0.9";
  BOOMR.window = w;


  // impl is a private object not reachable from outside the BOOMR object
  // users can set properties by passing in to the init() method
  impl = {
    // properties
    beacon_url: "",
    // strip out everything except last two parts of hostname.
    // This doesn't work well for domains that end with a country tld,
    // but we allow the developer to override site_domain for that.
    // You can disable all cookies by setting site_domain to a falsy value
    site_domain: w.location.hostname.
    replace(/.*?([^.]+\.[^.]+)\.?$/, '$1').
    toLowerCase(),
    //! User's ip address determined on the server.  Used for the BA cookie
    user_ip: '',

    onloadfired: false,

    handlers_attached: false,
    events: {
      "page_ready": [],
      "page_unload": [],
      "dom_loaded": [],
      "visibility_changed": [],
      "before_beacon": [],
      "click": []
    },

    vars: {},

    disabled_plugins: {},

    onclick_handler: function(ev) {
      var target;
      if (!ev) {
        ev = w.event;
      }
      if (ev.target) {
        target = ev.target;
      } else if (ev.srcElement) {
        target = ev.srcElement;
      }
      if (target.nodeType === 3) { // defeat Safari bug
        target = target.parentNode;
      }

      // don't capture clicks on flash objects
      // because of context slowdowns in PepperFlash
      if (target && target.nodeName.toUpperCase() === "OBJECT" && target.type === "application/x-shockwave-flash") {
        return;
      }
      impl.fireEvent("click", target);
    },

    fireEvent: function(e_name, data) {
      var i, h, e;
      if (!this.events.hasOwnProperty(e_name)) {
        return false;
      }

      e = this.events[e_name];

      for (i = 0; i < e.length; i++) {
        h = e[i];
        h[0].call(h[2], data, h[1]);
      }

      return true;
    },

    addListener: function(el, sType, fn) {
      if (el.addEventListener) {
        el.addEventListener(sType, fn, false);
      } else if (el.attachEvent) {
        el.attachEvent("on" + sType, fn);
      }
    }
  };


  // We create a boomr object and then copy all its properties to BOOMR so that
  // we don't overwrite anything additional that was added to BOOMR before this
  // was called... for example, a plugin.
  boomr = {
    t_start: BOOMR_start,
    t_end: null,

    // Utility functions
    utils: {
      objectToString: function(o, separator) {
        var value = [],
          k;

        if (!o || typeof o !== "object") {
          return o;
        }
        if (separator === undefined) {
          separator = "\n\t";
        }

        for (k in o) {
          if (Object.prototype.hasOwnProperty.call(o, k)) {
            value.push(encodeURIComponent(k) + '=' + encodeURIComponent(o[k]));
          }
        }

        return value.join(separator);
      },

      getCookie: function(name) {
        if (!name) {
          return null;
        }

        name = ' ' + name + '=';

        var i, cookies;
        cookies = ' ' + d.cookie + ';';
        if ((i = cookies.indexOf(name)) >= 0) {
          i += name.length;
          cookies = cookies.substring(i, cookies.indexOf(';', i));
          return cookies;
        }

        return null;
      },

      setCookie: function(name, subcookies, max_age) {
        var value, nameval, c, exp;

        if (!name || !impl.site_domain) {
          return false;
        }

        value = this.objectToString(subcookies, "&");
        nameval = name + '=' + value;

        c = [nameval, "path=/", "domain=" + impl.site_domain];
        if (max_age) {
          exp = new Date();
          exp.setTime(exp.getTime() + max_age * 1000);
          exp = exp.toGMTString();
          c.push("expires=" + exp);
        }

        if (nameval.length < 4000) {
          d.cookie = c.join('; ');
          // confirm cookie was set (could be blocked by user's settings, etc.)
          return (value === this.getCookie(name));
        }

        return false;
      },

      getSubCookies: function(cookie) {
        var cookies_a,
          i, l, kv,
          cookies = {};

        if (!cookie) {
          return null;
        }

        cookies_a = cookie.split('&');

        if (cookies_a.length === 0) {
          return null;
        }

        for (i = 0, l = cookies_a.length; i < l; i++) {
          kv = cookies_a[i].split('=');
          kv.push(""); // just in case there's no value
          cookies[decodeURIComponent(kv[0])] = decodeURIComponent(kv[1]);
        }

        return cookies;
      },

      removeCookie: function(name) {
        return this.setCookie(name, {}, 0);
      },

      pluginConfig: function(o, config, plugin_name, properties) {
        var i, props = 0;

        if (!config || !config[plugin_name]) {
          return false;
        }

        for (i = 0; i < properties.length; i++) {
          if (config[plugin_name][properties[i]] !== undefined) {
            o[properties[i]] = config[plugin_name][properties[i]];
            props++;
          }
        }

        return (props > 0);
      }
    },

    init: function(config) {
      var i, k,
        properties = ["beacon_url", "site_domain", "user_ip"];

      if (!config) {
        config = {};
      }

      for (i = 0; i < properties.length; i++) {
        if (config[properties[i]] !== undefined) {
          impl[properties[i]] = config[properties[i]];
        }
      }

      if (config.log !== undefined) {
        this.log = config.log;
      }
      if (!this.log) {
        this.log = function( /* m,l,s */ ) {};
      }

      for (k in this.plugins) {
        if (this.plugins.hasOwnProperty(k)) {
          // config[pugin].enabled has been set to false
          if (config[k] && config[k].hasOwnProperty("enabled") && config[k].enabled === false) {
            impl.disabled_plugins[k] = 1;
            continue;
          } else if (impl.disabled_plugins[k]) {
            delete impl.disabled_plugins[k];
          }

          // plugin exists and has an init method
          if (typeof this.plugins[k].init === "function") {
            this.plugins[k].init(config);
          }
        }
      }

      if (impl.handlers_attached) {
        return this;
      }

      // The developer can override onload by setting autorun to false
      if (!impl.onloadfired && (config.autorun === undefined || config.autorun !== false)) {
        if (d.readyState && d.readyState === "complete") {
          this.setImmediate(BOOMR.page_ready, null, null, BOOMR);
        } else {
          if (w.onpagehide || w.onpagehide === null) {
            impl.addListener(w, "pageshow", BOOMR.page_ready);
          } else {
            impl.addListener(w, "load", BOOMR.page_ready);
          }
        }
      }

      impl.addListener(w, "DOMContentLoaded", function() {
        impl.fireEvent("dom_loaded");
      });

      (function() {
        // visibilitychange is useful to detect if the page loaded through prerender
        // or if the page never became visible
        // http://www.w3.org/TR/2011/WD-page-visibility-20110602/
        // http://www.nczonline.net/blog/2011/08/09/introduction-to-the-page-visibility-api/
        var fire_visible = function() {
          impl.fireEvent("visibility_changed");
        };
        if (d.webkitVisibilityState) {
          impl.addListener(d, "webkitvisibilitychange", fire_visible);
        } else if (d.msVisibilityState) {
          impl.addListener(d, "msvisibilitychange", fire_visible);
        } else if (d.visibilityState) {
          impl.addListener(d, "visibilitychange", fire_visible);
        }

        impl.addListener(d, "mouseup", impl.onclick_handler);

        if (!w.onpagehide && w.onpagehide !== null) {
          // This must be the last one to fire
          // We only clear w on browsers that don't support onpagehide because
          // those that do are new enough to not have memory leak problems of
          // some older browsers
          impl.addListener(w, "unload", function() {
            BOOMR.window = w = null;
          });
        }
      }());

      impl.handlers_attached = true;
      return this;
    },

    // The page dev calls this method when they determine the page is usable.
    // Only call this if autorun is explicitly set to false
    page_ready: function() {
      if (impl.onloadfired) {
        return this;
      }
      impl.fireEvent("page_ready");
      impl.onloadfired = true;
      return this;
    },

    setImmediate: function(fn, data, cb_data, cb_scope) {
      var cb = function() {
        fn.call(cb_scope || null, data, cb_data || {});
        cb = null;
      };

      if (w.setImmediate) {
        w.setImmediate(cb);
      } else if (w.msSetImmediate) {
        w.msSetImmediate(cb);
      } else if (w.webkitSetImmediate) {
        w.webkitSetImmediate(cb);
      } else if (w.mozSetImmediate) {
        w.mozSetImmediate(cb);
      } else {
        setTimeout(cb, 10);
      }
    },

    subscribe: function(e_name, fn, cb_data, cb_scope) {
      var i, h, e, unload_handler;

      if (!impl.events.hasOwnProperty(e_name)) {
        return this;
      }

      e = impl.events[e_name];

      // don't allow a handler to be attached more than once to the same event
      for (i = 0; i < e.length; i++) {
        h = e[i];
        if (h[0] === fn && h[1] === cb_data && h[2] === cb_scope) {
          return this;
        }
      }
      e.push([fn, cb_data || {}, cb_scope || null]);

      // attaching to page_ready after onload fires, so call soon
      if (e_name === 'page_ready' && impl.onloadfired) {
        this.setImmediate(fn, null, cb_data, cb_scope);
      }

      // Attach unload handlers directly to the window.onunload and
      // window.onbeforeunload events. The first of the two to fire will clear
      // fn so that the second doesn't fire. We do this because technically
      // onbeforeunload is the right event to fire, but all browsers don't
      // support it.  This allows us to fall back to onunload when onbeforeunload
      // isn't implemented
      if (e_name === 'page_unload') {
        unload_handler = function(ev) {
          if (fn) {
            fn.call(cb_scope, ev || w.event, cb_data);
          }
        };
        // pagehide is for iOS devices
        // see http://www.webkit.org/blog/516/webkit-page-cache-ii-the-unload-event/
        if (w.onpagehide || w.onpagehide === null) {
          impl.addListener(w, "pagehide", unload_handler);
        } else {
          impl.addListener(w, "unload", unload_handler);
        }
        impl.addListener(w, "beforeunload", unload_handler);
      }

      return this;
    },

    addVar: function(name, value) {
      if (typeof name === "string") {
        impl.vars[name] = value;
      } else if (typeof name === "object") {
        var o = name,
          k;
        for (k in o) {
          if (o.hasOwnProperty(k)) {
            impl.vars[k] = o[k];
          }
        }
      }
      return this;
    },

    removeVar: function(arg0) {
      var i, params;
      if (!arguments.length) {
        return this;
      }

      if (arguments.length === 1 && Object.prototype.toString.apply(arg0) === "[object Array]") {
        params = arg0;
      } else {
        params = arguments;
      }

      for (i = 0; i < params.length; i++) {
        if (impl.vars.hasOwnProperty(params[i])) {
          delete impl.vars[params[i]];
        }
      }

      return this;
    },

    sendBeacon: function() {
      var k, url, img, nparams = 0;

      // At this point someone is ready to send the beacon.  We send
      // the beacon only if all plugins have finished doing what they
      // wanted to do
      for (k in this.plugins) {
        if (this.plugins.hasOwnProperty(k)) {
          if (impl.disabled_plugins[k]) {
            continue;
          }
          if (!this.plugins[k].is_complete()) {
            return this;
          }
        }
      }

      impl.vars.v = BOOMR.version;
      impl.vars.u = d.URL.replace(/#.*/, '');
      // use d.URL instead of location.href because of a safari bug
      if (w !== window) {
        impl.vars["if"] = "";
      }

      // If we reach here, all plugins have completed
      impl.fireEvent("before_beacon", impl.vars);

      // Don't send a beacon if no beacon_url has been set
      // you would do this if you want to do some fancy beacon handling
      // in the `before_beacon` event instead of a simple GET request
      if (!impl.beacon_url) {
        return this;
      }

      // if there are already url parameters in the beacon url,
      // change the first parameter prefix for the boomerang url parameters to &

      url = [];

      for (k in impl.vars) {
        if (impl.vars.hasOwnProperty(k)) {
          nparams++;
          url.push(encodeURIComponent(k) + "=" + (
            impl.vars[k] === undefined || impl.vars[k] === null ? '' : encodeURIComponent(impl.vars[k])
          ));
        }
      }

      url = impl.beacon_url + ((impl.beacon_url.indexOf('?') > -1) ? '&' : '?') + url.join('&');

      BOOMR.debug("Sending url: " + url.replace(/&/g, "\n\t"));

      // only send beacon if we actually have something to beacon back
      if (nparams) {
        img = new Image();
        img.src = url;
      }

      return this;
    }

  };

  delete BOOMR_start;

  (function() {
    var make_logger = function(l) {
      return function(m, s) {
        this.log(m, l, "boomerang" + (s ? "." + s : ""));
        return this;
      };
    };

    boomr.debug = make_logger("debug");
    boomr.info = make_logger("info");
    boomr.warn = make_logger("warn");
    boomr.error = make_logger("error");
  }());

  if (w.YAHOO && w.YAHOO.widget && w.YAHOO.widget.Logger) {
    boomr.log = w.YAHOO.log;
  } else if (w.Y && w.Y.log) {
    boomr.log = w.Y.log;
  } else if (typeof console === "object" && console.log !== undefined) {
    boomr.log = function(m, l, s) {
      console.log(s + ": [" + l + "] " + m);
    };
  }


  for (k in boomr) {
    if (boomr.hasOwnProperty(k)) {
      BOOMR[k] = boomr[k];
    }
  }

  BOOMR.plugins = BOOMR.plugins || {};

}(window));

// end of boomerang beaconing section
// Now we start built in plugins.

/*
 * Copyright (c) 2011, Yahoo! Inc.  All rights reserved.
 * Copyright (c) 2012, Log-Normal, Inc.  All rights reserved.
 * Copyrights licensed under the BSD License. See the accompanying LICENSE.txt file for terms.
 */

// This is the Bandwidth & Latency plugin abbreviated to BW
(function() {
  var impl, images;

  BOOMR = BOOMR || {};
  BOOMR.plugins = BOOMR.plugins || {};

  // We choose image sizes so that we can narrow down on a bandwidth range as
  // soon as possible the sizes chosen correspond to bandwidth values of
  // 14-64kbps, 64-256kbps, 256-1024kbps, 1-2Mbps, 2-8Mbps, 8-30Mbps & 30Mbps+
  // Anything below 14kbps will probably timeout before the test completes
  // Anything over 60Mbps will probably be unreliable since latency will make up
  // the largest part of download time. If you want to extend this further to
  // cover 100Mbps & 1Gbps networks, use image sizes of 19,200,000 & 153,600,000
  // bytes respectively
  // See https://spreadsheets.google.com/ccc?key=0AplxPyCzmQi6dDRBN2JEd190N1hhV1N5cHQtUVdBMUE&hl=en_GB
  // for a spreadsheet with the details
  images = [{
    name: "image-0.png",
    size: 11483,
    timeout: 1400
  }, {
    name: "image-1.png",
    size: 40658,
    timeout: 1200
  }, {
    name: "image-2.png",
    size: 164897,
    timeout: 1300
  }, {
    name: "image-3.png",
    size: 381756,
    timeout: 1500
  }, {
    name: "image-4.png",
    size: 1234664,
    timeout: 1200
  }, {
    name: "image-5.png",
    size: 4509613,
    timeout: 1200
  }, {
    name: "image-6.png",
    size: 9084559,
    timeout: 1200
  }];

  images.end = images.length;
  images.start = 0;

  // abuse arrays to do the latency test simply because it avoids a bunch of
  // branches in the rest of the code.
  // I'm sorry Douglas
  images.l = {
    name: "image-l.gif",
    size: 35,
    timeout: 1000
  };

  // private object
  impl = {
    // properties
    base_url: '',
    timeout: 15000,
    nruns: 5,
    latency_runs: 10,
    user_ip: '',
    cookie_exp: 7 * 86400,
    cookie: 'BA',

    // state
    results: [],
    latencies: [],
    latency: null,
    runs_left: 0,
    aborted: false,
    complete: true, // defaults to true so we don't block other plugins if this cannot start.
    // init sets it to false
    running: false,
    initialized: false,

    // methods

    // numeric comparator.  Returns negative number if a < b, positive if a > b and 0 if they're equal
    // used to sort an array numerically
    ncmp: function(a, b) {
      return (a - b);
    },

    // Calculate the interquartile range of an array of data points
    iqr: function(a) {
      var l = a.length - 1,
        q1, q3, fw, b = [],
        i;

      q1 = (a[Math.floor(l * 0.25)] + a[Math.ceil(l * 0.25)]) / 2;
      q3 = (a[Math.floor(l * 0.75)] + a[Math.ceil(l * 0.75)]) / 2;

      fw = (q3 - q1) * 1.5;

      l++;

      for (i = 0; i < l && a[i] < q3 + fw; i++) {
        if (a[i] > q1 - fw) {
          b.push(a[i]);
        }
      }

      return b;
    },

    calc_latency: function() {
      var i, n,
        sum = 0,
        sumsq = 0,
        amean, median,
        std_dev, std_err,
        lat_filtered;

      // We first do IQR filtering and use the resulting data set
      // for all calculations
      lat_filtered = this.iqr(this.latencies.sort(this.ncmp));
      n = lat_filtered.length;

      BOOMR.debug(lat_filtered, "bw");

      // First we get the arithmetic mean, standard deviation and standard error
      // We ignore the first since it paid the price of DNS lookup, TCP connect
      // and slow start
      for (i = 1; i < n; i++) {
        sum += lat_filtered[i];
        sumsq += lat_filtered[i] * lat_filtered[i];
      }

      n--; // Since we started the loop with 1 and not 0

      amean = Math.round(sum / n);

      std_dev = Math.sqrt(sumsq / n - sum * sum / (n * n));

      // See http://en.wikipedia.org/wiki/1.96 and http://en.wikipedia.org/wiki/Standard_error_%28statistics%29
      std_err = (1.96 * std_dev / Math.sqrt(n)).toFixed(2);

      std_dev = std_dev.toFixed(2);


      n = lat_filtered.length - 1;

      median = Math.round(
        (lat_filtered[Math.floor(n / 2)] + lat_filtered[Math.ceil(n / 2)]) / 2
      );

      return {
        mean: amean,
        median: median,
        stddev: std_dev,
        stderr: std_err
      };
    },

    calc_bw: function() {
      var i, j, n = 0,
        r, bandwidths = [],
        bandwidths_corrected = [],
        sum = 0,
        sumsq = 0,
        sum_corrected = 0,
        sumsq_corrected = 0,
        amean, std_dev, std_err, median,
        amean_corrected, std_dev_corrected, std_err_corrected, median_corrected,
        nimgs, bw, bw_c, debug_info = [];

      for (i = 0; i < this.nruns; i++) {
        if (!this.results[i] || !this.results[i].r) {
          continue;
        }

        r = this.results[i].r;

        // the next loop we iterate through backwards and only consider the largest
        // 3 images that succeeded that way we don't consider small images that
        // downloaded fast without really saturating the network
        nimgs = 0;
        for (j = r.length - 1; j >= 0 && nimgs < 3; j--) {
          // if we hit an undefined image time, we skipped everything before this
          if (!r[j]) {
            break;
          }
          if (r[j].t === null) {
            continue;
          }

          n++;
          nimgs++;

          // multiply by 1000 since t is in milliseconds and not seconds
          bw = images[j].size * 1000 / r[j].t;
          bandwidths.push(bw);

          bw_c = images[j].size * 1000 / (r[j].t - this.latency.mean);
          bandwidths_corrected.push(bw_c);

          if (r[j].t < this.latency.mean) {
            debug_info.push(j + "_" + r[j].t);
          }
        }
      }

      BOOMR.debug('got ' + n + ' readings', "bw");

      BOOMR.debug('bandwidths: ' + bandwidths, "bw");
      BOOMR.debug('corrected: ' + bandwidths_corrected, "bw");

      // First do IQR filtering since we use the median here
      // and should use the stddev after filtering.
      if (bandwidths.length > 3) {
        bandwidths = this.iqr(bandwidths.sort(this.ncmp));
        bandwidths_corrected = this.iqr(bandwidths_corrected.sort(this.ncmp));
      } else {
        bandwidths = bandwidths.sort(this.ncmp);
        bandwidths_corrected = bandwidths_corrected.sort(this.ncmp);
      }

      BOOMR.debug('after iqr: ' + bandwidths, "bw");
      BOOMR.debug('corrected: ' + bandwidths_corrected, "bw");

      // Now get the mean & median.
      // Also get corrected values that eliminate latency
      n = Math.max(bandwidths.length, bandwidths_corrected.length);
      for (i = 0; i < n; i++) {
        if (i < bandwidths.length) {
          sum += bandwidths[i];
          sumsq += Math.pow(bandwidths[i], 2);
        }
        if (i < bandwidths_corrected.length) {
          sum_corrected += bandwidths_corrected[i];
          sumsq_corrected += Math.pow(bandwidths_corrected[i], 2);
        }
      }

      n = bandwidths.length;
      amean = Math.round(sum / n);
      std_dev = Math.sqrt(sumsq / n - Math.pow(sum / n, 2));
      std_err = Math.round(1.96 * std_dev / Math.sqrt(n));
      std_dev = Math.round(std_dev);

      n = bandwidths.length - 1;
      median = Math.round(
        (bandwidths[Math.floor(n / 2)] + bandwidths[Math.ceil(n / 2)]) / 2
      );

      n = bandwidths_corrected.length;
      amean_corrected = Math.round(sum_corrected / n);
      std_dev_corrected = Math.sqrt(sumsq_corrected / n - Math.pow(sum_corrected / n, 2));
      std_err_corrected = (1.96 * std_dev_corrected / Math.sqrt(n)).toFixed(2);
      std_dev_corrected = std_dev_corrected.toFixed(2);

      n = bandwidths_corrected.length - 1;
      median_corrected = Math.round(
        (
          bandwidths_corrected[Math.floor(n / 2)] + bandwidths_corrected[Math.ceil(n / 2)]
        ) / 2
      );

      BOOMR.debug('amean: ' + amean + ', median: ' + median, "bw");
      BOOMR.debug('corrected amean: ' + amean_corrected + ', ' + 'median: ' + median_corrected, "bw");

      return {
        mean: amean,
        stddev: std_dev,
        stderr: std_err,
        median: median,
        mean_corrected: amean_corrected,
        stddev_corrected: std_dev_corrected,
        stderr_corrected: std_err_corrected,
        median_corrected: median_corrected,
        debug_info: debug_info
      };
    },

    defer: function(method) {
      var that = this;
      return setTimeout(function() {
        method.call(that);
        that = null;
      }, 10);
    },

    load_img: function(i, run, callback) {
      var url = this.base_url + images[i].name + '?t=' + (new Date().getTime()) + Math.random(), // Math.random() is slow, but we get it before we start the timer
        timer = 0,
        tstart = 0,
        img = new Image(),
        that = this;

      img.onload = function() {
        img.onload = img.onerror = null;
        img = null;
        clearTimeout(timer);
        if (callback) {
          callback.call(that, i, tstart, run, true);
        }
        that = callback = null;
      };
      img.onerror = function() {
        img.onload = img.onerror = null;
        img = null;
        clearTimeout(timer);
        if (callback) {
          callback.call(that, i, tstart, run, false);
        }
        that = callback = null;
      };

      // the timeout does not abort download of the current image, it just sets an
      // end of loop flag so we don't attempt download of the next image we still
      // need to wait until onload or onerror fire to be sure that the image
      // download isn't using up bandwidth.  This also saves us if the timeout
      // happens on the first image.  If it didn't, we'd have nothing to measure.
      timer = setTimeout(function() {
          if (callback) {
            callback.call(that, i, tstart, run, null);
          }
        },
        images[i].timeout + Math.min(400, this.latency ? this.latency.mean : 400)
      );

      tstart = new Date().getTime();
      img.src = url;
    },

    lat_loaded: function(i, tstart, run, success) {
      if (run !== this.latency_runs + 1) {
        return;
      }

      if (success !== null) {
        var lat = new Date().getTime() - tstart;
        this.latencies.push(lat);
      }
      // we've got all the latency images at this point,
      // so we can calculate latency
      if (this.latency_runs === 0) {
        this.latency = this.calc_latency();
      }

      this.defer(this.iterate);
    },

    img_loaded: function(i, tstart, run, success) {
      if (run !== this.runs_left + 1) {
        return;
      }

      if (this.results[this.nruns - run].r[i]) { // already called on this image
        return;
      }

      // if timeout, then we set the next image to the end of loop marker
      if (success === null) {
        this.results[this.nruns - run].r[i + 1] = {
          t: null,
          state: null,
          run: run
        };
        return;
      }

      var result = {
        start: tstart,
        end: new Date().getTime(),
        t: null,
        state: success,
        run: run
      };
      if (success) {
        result.t = result.end - result.start;
      }
      this.results[this.nruns - run].r[i] = result;

      // we terminate if an image timed out because that means the connection is
      // too slow to go to the next image
      if (i >= images.end - 1 || this.results[this.nruns - run].r[i + 1] !== undefined) {
        BOOMR.debug(this.results[this.nruns - run], "bw");
        // First run is a pilot test to decide what the largest image
        // that we can download is. All following runs only try to
        // download this image
        if (run === this.nruns) {
          images.start = i;
        }
        this.defer(this.iterate);
      } else {
        this.load_img(i + 1, run, this.img_loaded);
      }
    },

    finish: function() {
      if (!this.latency) {
        this.latency = this.calc_latency();
      }
      var bw = this.calc_bw(),
        o = {
          bw: bw.median_corrected,
          bw_err: parseFloat(bw.stderr_corrected, 10),
          lat: this.latency.mean,
          lat_err: parseFloat(this.latency.stderr, 10),
          bw_time: Math.round(new Date().getTime() / 1000)
        };

      BOOMR.addVar(o);
      if (bw.debug_info.length > 0) {
        BOOMR.addVar("bw_debug", bw.debug_info.join(','));
      }

      // If we have an IP address we can make the BA cookie persistent for a while
      // because we'll recalculate it if necessary (when the user's IP changes).
      if (!isNaN(o.bw) && o.bw > 0) {
        BOOMR.utils.setCookie(this.cookie, {
          ba: Math.round(o.bw),
          be: o.bw_err,
          l: o.lat,
          le: o.lat_err,
          ip: this.user_ip,
          t: o.bw_time
        }, (this.user_ip ? this.cookie_exp : 0));
      }

      this.complete = true;
      BOOMR.sendBeacon();
      this.running = false;
    },

    iterate: function() {
      if (this.aborted) {
        return false;
      }

      if (!this.runs_left) {
        this.finish();
      } else if (this.latency_runs) {
        this.load_img('l', this.latency_runs--, this.lat_loaded);
      } else {
        this.results.push({
          r: []
        });
        this.load_img(images.start, this.runs_left--, this.img_loaded);
      }
    },

    setVarsFromCookie: function(cookies) {
      var ba = parseInt(cookies.ba, 10),
        bw_e = parseFloat(cookies.be, 10),
        lat = parseInt(cookies.l, 10) || 0,
        lat_e = parseFloat(cookies.le, 10) || 0,
        c_sn = cookies.ip.replace(/\.\d+$/, '0'), // Note this is IPv4 only
        t = parseInt(cookies.t, 10),
        p_sn = this.user_ip.replace(/\.\d+$/, '0'),

        // We use the subnet instead of the IP address because some people
        // on DHCP with the same ISP may get different IPs on the same subnet
        // every time they log in

        t_now = Math.round((new Date().getTime()) / 1000); // seconds

      // If the subnet changes or the cookie is more than 7 days old,
      // then we recheck the bandwidth, else we just use what's in the cookie
      if (c_sn === p_sn && t >= t_now - this.cookie_exp && ba > 0) {
        this.complete = true;
        BOOMR.addVar({
          'bw': ba,
          'lat': lat,
          'bw_err': bw_e,
          'lat_err': lat_e
        });

        return true;
      }

      return false;
    }

  };

  BOOMR.plugins.BW = {
    init: function(config) {
      var cookies;

      if (impl.initialized) {
        return this;
      }

      BOOMR.utils.pluginConfig(impl, config, "BW", ["base_url", "timeout", "nruns", "cookie", "cookie_exp"]);

      if (config && config.user_ip) {
        impl.user_ip = config.user_ip;
      }

      if (!impl.base_url) {
        return this;
      }

      images.start = 0;
      impl.runs_left = impl.nruns;
      impl.latency_runs = 10;
      impl.results = [];
      impl.latencies = [];
      impl.latency = null;
      impl.complete = false;
      impl.aborted = false;

      BOOMR.removeVar('ba', 'ba_err', 'lat', 'lat_err');

      cookies = BOOMR.utils.getSubCookies(BOOMR.utils.getCookie(impl.cookie));

      if (!cookies || !cookies.ba || !impl.setVarsFromCookie(cookies)) {
        BOOMR.subscribe("page_ready", this.run, null, this);
        BOOMR.subscribe("page_unload", this.skip, null, this);
      }

      impl.initialized = true;

      return this;
    },

    run: function() {
      if (impl.running || impl.complete) {
        return this;
      }

      if (BOOMR.window.location.protocol === 'https:') {
        // we don't run the test for https because SSL stuff will mess up b/w
        // calculations we could run the test itself over HTTP, but then IE
        // will complain about insecure resources, so the best is to just bail
        // and hope that the user gets the cookie from some other page

        BOOMR.info("HTTPS detected, skipping bandwidth test", "bw");
        impl.complete = true;
        BOOMR.sendBeacon();
        return this;
      }

      impl.running = true;

      setTimeout(this.abort, impl.timeout);

      impl.defer(impl.iterate);

      return this;
    },

    abort: function() {
      impl.aborted = true;
      if (impl.running) {
        impl.finish(); // we don't defer this call because it might be called from
        // onunload and we want the entire chain to complete
        // before we return
      }
      return this;
    },

    skip: function() {
      // this is called on unload, so we should abort the test

      // it's also possible that we didn't start, so sendBeacon never
      // gets called.  Let's set our complete state and call sendBeacon.
      // This happens if onunload fires before onload

      if (!impl.complete) {
        impl.complete = true;
        BOOMR.sendBeacon();
      }

      return this;
    },

    is_complete: function() {
      return impl.complete;
    }
  };

}());
// End of BW plugin

/* Copyright (c) 2010-2012 Marcus Westin */
this.JSON || (this.JSON = {}),
  function() {
    function f(e) {
      return e < 10 ? "0" + e : e
    }

    function quote(e) {
      return escapable.lastIndex = 0, escapable.test(e) ? '"' + e.replace(escapable, function(e) {
        var t = meta[e];
        return typeof t == "string" ? t : "\\u" + ("0000" + e.charCodeAt(0).toString(16)).slice(-4)
      }) + '"' : '"' + e + '"'
    }

    function str(e, t) {
      var n, r, i, s, o = gap,
        u, a = t[e];
      a && typeof a == "object" && typeof a.toJSON == "function" && (a = a.toJSON(e)), typeof rep == "function" && (a = rep.call(t, e, a));
      switch (typeof a) {
        case "string":
          return quote(a);
        case "number":
          return isFinite(a) ? String(a) : "null";
        case "boolean":
        case "null":
          return String(a);
        case "object":
          if (!a) return "null";
          gap += indent, u = [];
          if (Object.prototype.toString.apply(a) === "[object Array]") {
            s = a.length;
            for (n = 0; n < s; n += 1) u[n] = str(n, a) || "null";
            return i = u.length === 0 ? "[]" : gap ? "[\n" + gap + u.join(",\n" + gap) + "\n" + o + "]" : "[" + u.join(",") + "]", gap = o, i
          }
          if (rep && typeof rep == "object") {
            s = rep.length;
            for (n = 0; n < s; n += 1) r = rep[n], typeof r == "string" && (i = str(r, a), i && u.push(quote(r) + (gap ? ": " : ":") + i))
          } else
            for (r in a) Object.hasOwnProperty.call(a, r) && (i = str(r, a), i && u.push(quote(r) + (gap ? ": " : ":") + i));
          return i = u.length === 0 ? "{}" : gap ? "{\n" + gap + u.join(",\n" + gap) + "\n" + o + "}" : "{" + u.join(",") + "}", gap = o, i
      }
    }
    typeof Date.prototype.toJSON != "function" && (Date.prototype.toJSON = function(e) {
      return isFinite(this.valueOf()) ? this.getUTCFullYear() + "-" + f(this.getUTCMonth() + 1) + "-" + f(this.getUTCDate()) + "T" + f(this.getUTCHours()) + ":" + f(this.getUTCMinutes()) + ":" + f(this.getUTCSeconds()) + "Z" : null
    }, String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function(e) {
      return this.valueOf()
    });
    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
      escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
      gap, indent, meta = {
        "\b": "\\b",
        " ": "\\t",
        "\n": "\\n",
        "\f": "\\f",
        "\r": "\\r",
        '"': '\\"',
        "\\": "\\\\"
      },
      rep;
    typeof JSON.stringify != "function" && (JSON.stringify = function(e, t, n) {
      var r;
      gap = "", indent = "";
      if (typeof n == "number")
        for (r = 0; r < n; r += 1) indent += " ";
      else typeof n == "string" && (indent = n);
      rep = t;
      if (!t || typeof t == "function" || typeof t == "object" && typeof t.length == "number") return str("", {
        "": e
      });
      throw new Error("JSON.stringify")
    }), typeof JSON.parse != "function" && (JSON.parse = function(text, reviver) {
      function walk(e, t) {
        var n, r, i = e[t];
        if (i && typeof i == "object")
          for (n in i) Object.hasOwnProperty.call(i, n) && (r = walk(i, n), r !== undefined ? i[n] = r : delete i[n]);
        return reviver.call(e, t, i)
      }
      var j;
      text = String(text), cx.lastIndex = 0, cx.test(text) && (text = text.replace(cx, function(e) {
        return "\\u" + ("0000" + e.charCodeAt(0).toString(16)).slice(-4)
      }));
      if (/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) return j = eval("(" + text + ")"), typeof reviver == "function" ? walk({
        "": j
      }, "") : j;
      throw new SyntaxError("JSON.parse")
    })
  }(),
  function() {
    function o() {
      try {
        return r in t && t[r]
      } catch (e) {
        return !1
      }
    }
    var e = {},
      t = window,
      n = t.document,
      r = "localStorage",
      i = "__storejs__",
      s;
    e.disabled = !1, e.set = function(e, t) {}, e.get = function(e) {}, e.remove = function(e) {}, e.clear = function() {}, e.transact = function(t, n, r) {
      var i = e.get(t);
      r == null && (r = n, n = null), typeof i == "undefined" && (i = n || {}), r(i), e.set(t, i)
    }, e.getAll = function() {}, e.serialize = function(e) {
      return JSON.stringify(e)
    }, e.deserialize = function(e) {
      if (typeof e != "string") return undefined;
      try {
        return JSON.parse(e)
      } catch (t) {
        return e || undefined
      }
    };
    if (o()) s = t[r], e.set = function(t, n) {
      return n === undefined ? e.remove(t) : (s.setItem(t, e.serialize(n)), n)
    }, e.get = function(t) {
      return e.deserialize(s.getItem(t))
    }, e.remove = function(e) {
      s.removeItem(e)
    }, e.clear = function() {
      s.clear()
    }, e.getAll = function() {
      var t = {};
      for (var n = 0; n < s.length; ++n) {
        var r = s.key(n);
        t[r] = e.get(r)
      }
      return t
    };
    else if (n.documentElement.addBehavior) {
      var u, a;
      try {
        a = new ActiveXObject("htmlfile"), a.open(), a.write('<script>document.w=window<' + '/script><iframe src="/favicon.ico"></iframe>'), a.close(), u = a.w.frames[0].document, s = u.createElement("div")
      } catch (f) {
        s = n.createElement("div"), u = n.body
      }

      function l(t) {
        return function() {
          var n = Array.prototype.slice.call(arguments, 0);
          n.unshift(s), u.appendChild(s), s.addBehavior("#default#userData"), s.load(r);
          var i = t.apply(e, n);
          return u.removeChild(s), i
        }
      }
      var c = new RegExp("[!\"#$%&'()*+,/\\\\:;<=>?@[\\]^`{|}~]", "g");

      function h(e) {
        return e.replace(c, "___")
      }
      e.set = l(function(t, n, i) {
        return n = h(n), i === undefined ? e.remove(n) : (t.setAttribute(n, e.serialize(i)), t.save(r), i)
      }), e.get = l(function(t, n) {
        return n = h(n), e.deserialize(t.getAttribute(n))
      }), e.remove = l(function(e, t) {
        t = h(t), e.removeAttribute(t), e.save(r)
      }), e.clear = l(function(e) {
        var t = e.XMLDocument.documentElement.attributes;
        e.load(r);
        for (var n = 0, i; i = t[n]; n++) e.removeAttribute(i.name);
        e.save(r)
      }), e.getAll = l(function(t) {
        var n = t.XMLDocument.documentElement.attributes,
          r = {};
        for (var i = 0, s; s = n[i]; ++i) {
          var o = h(s.name);
          r[s.name] = e.deserialize(t.getAttribute(o))
        }
        return r
      })
    }
    try {
      e.set(i, i), e.get(i) != i && (e.disabled = !0), e.remove(i)
    } catch (f) {
      e.disabled = !0
    }
    e.enabled = !e.disabled, typeof module != "undefined" && module.exports ? module.exports = e : typeof define == "function" && define.amd ? define(e) : this.store = window.store = e;
    window.store = e;
  }()

// Generated by CoffeeScript 1.6.2

var getLiwushuoIndexInfo, getLiwushuoAPIInfo, getAlibabaInfo, getSinaInfo, getTencentInfo, getTaobaoInfo, getDetailInfo, getAssetsCDNInfo, getBrowserInfo, getCookieEnable, getFlashVersion, getImageCDNInfo, getIp, getLDNS, getOSInfo, getPasswordControlInstalledText, getPasswordControlVersion, getUAString, isMac, jsonp, log;

log = function(msg, config) {
  var el, tagname, wrapperEl;

  msg = msg.split("：");
  msg = "<div style=\"\">\n  <span style=\"color:#666;\">" + msg[0] + "：</span>\n  <span>" + (msg.slice(1).join("：") || "") + "</span>\n</div>";
  tagname = config && config.tagname || "div";
  el = $("<" + tagname + ">" + msg + "</" + tagname + ">");
  if (typeof config === "string") {
    config = {
      "id": config
    };
  }
  if (!config || !config.id) {
    return el.appendTo("#log");
  }
  wrapperEl = $("#" + config.id);
  if (wrapperEl.length) {
    if (config.type === "clear") {
      wrapperEl.html("");
    }
    return el.appendTo(wrapperEl);
  } else {
    el.attr("id", config.id);
    return el.appendTo("#log");
  }
};

getIp = function(cb) {
  var URL;

  URL = "http://110.75.20.230/yunqian/troubleshooter/ip_proxy.php?callback=?";
  return $.getJSON(URL, cb);
};

getCookieEnable = function() {
  return document.cookie || navigator.cookieEnabled;
};

getFlashVersion = function() {
  var axo, e;

  try {
    try {
      axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
      try {
        axo.AllowScriptAccess = "always";
      } catch (_error) {
        e = _error;
        return "6,0,0";
      }
    } catch (_error) {
      e = _error;
    }
    return new ActiveXObject('ShockwaveFlash.ShockwaveFlash').GetVariable('$version').replace(/\D+/g, ',').match(/^,?(.+),?$/)[1];
  } catch (_error) {
    e = _error;
    try {
      if (navigator.mimeTypes["application/x-shockwave-flash"].enabledPlugin) {
        return (navigator.plugins["Shockwave Flash 2.0"] || navigator.plugins["Shockwave Flash"]).description.replace(/\D+/g, ",").match(/^,?(.+),?$/)[1];
      }
    } catch (_error) {
      e = _error;
    }
  }
  return "0,0,0";
};

getOSInfo = function() {
  var item, token, ua, _i, _len;

  ua = navigator.userAgent;
  token = [
    ["Windows NT 5.1", "WinXP"],
    ["Windows NT 6.0", "WinVista"],
    ["Windows NT 6.1", "Win7"],
    ["Windows NT 5.2", "Win2003"],
    ["Windows NT 5.0", "Win2000"],
    ["Macintosh", "Macintosh"],
    ["Windows", "WinOther"],
    ["Ubuntu", "Ubuntu"],
    ["Linux", "Linux"]
  ];
  for (_i = 0, _len = token.length; _i < _len; _i++) {
    item = token[_i];
    if (ua.indexOf(item[0]) > -1) {
      return item[1];
    }
  }
  return "Other";
};

getUAString = function() {
  return navigator.userAgent;
};

getBrowserInfo = function() {
  var item, token, ua, _i, _len;

  ua = navigator.userAgent;
  token = ["Opera", "Chrome", "Safari", "MSIE 6", "MSIE 7", "MSIE 8", "Firefox"];
  for (_i = 0, _len = token.length; _i < _len; _i++) {
    item = token[_i];
    if (ua.indexOf(item) > -1) {
      return item.replace(" ", "");
    }
  }
  return "Other";
};

getImageCDNInfo = function(cb) {
  var check, count, imgs, item, _i, _len, _results;

  imgs = [
    ["http://img03.liwushuo.com/image/151207/ds0pz50lf.jpg-w720", 720, 720],
    ["http://img01.liwushuo.com/image/151208/nmmb2ev3p.jpg-w720", 720, 720],
    ["http://img02.liwushuo.com/image/151208/a72ixj82h.jpg-w720", 720, 720]
  ];
  count = 0;
  check = function() {
    var arr, item;

    count = count + 1;
    if (count < imgs.length) {
      return;
    }
    arr = (function() {
      var _i, _len, _results;

      _results = [];
      for (_i = 0, _len = imgs.length; _i < _len; _i++) {
        item = imgs[_i];
        if (item[3]) {
          _results.push(item);
        }
      }
      return _results;
    })();
    return typeof cb === "function" ? cb(arr.length, imgs.length) : void 0;
  };
  _results = [];
  for (_i = 0, _len = imgs.length; _i < _len; _i++) {
    item = imgs[_i];
    _results.push((function() {
      var height, img, j, sep, source, width;

      source = item[0], width = item[1], height = item[2];
      img = new Image();
      sep = source.indexOf("?") > -1 ? "&" : "?";
      img.src = "" + source + sep + "t=" + (+new Date());
      j = item;
      img.onload = function() {
        j.push(img.width === width && img.height === height);
        return check();
      };
      return img.onerror = function() {
        j.push(false);
        return check();
      };
    })());
  }
  return _results;
};

getTencentInfo = function(cb) {
  var asset, assets;

  asset = "http://www.qq.com";
  return $.ajax({
    url: asset,
    dataType: "script",
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

getSinaInfo = function(cb) {
  var asset, assets;

  asset = "http://www.sina.com.cn";
  return $.ajax({
    url: asset,
    dataType: "script",
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

getTaobaoInfo = function(cb) {
  var asset, assets;

  asset = "http://www.taobao.com";
  return $.ajax({
    url: asset,
    dataType: "script",
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

getDetailInfo = function(cb) {
  var asset, assets;

  asset = "http://item.taobao.com/item.htm?id=11111111";
  return $.ajax({
    url: asset,
    dataType: "script",
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

getAlibabaInfo = function(cb) {
  var asset, assets;

  asset = "http://www.alibaba.com";
  return $.ajax({
    url: asset,
    dataType: "script",
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

getAssetsCDNInfo = function(cb) {
  var asset, assets;

  asset = "http://g.tbcdn.cn/kissy/k/1.3.1/sizzle-min.js";
  return $.ajax({
    url: asset,
    dataType: "script",
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

isMac = function() {
  return navigator.platform === "MacIntel";
};

getPasswordControlVersion = function(cb) {
  light.has('page/products') || light.register('page/products');
  light.has('page/scProducts') || light.register('page/scProducts', light, []);
  return light.ready(function() {
    var edit, options, s;

    s = alipay.security;
    edit;
    options = {
      container: "payPassword_container",
      id: "edit_payPassword",
      name: "edit_payPassword",
      hidnId: "payPassword",
      width: "180",
      height: "24",
      tabindex: "",
      passwordMode: "1",
      timestamp: "8263170979",
      pk: "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDS92pDVyWNT7dzG9zH0opH44z9FayCZTX5iqGUxUjPi667IkyaqrsmDPqKsJp47lJ29lzs+Qv8zjPPdmnxjFteMrfpc4ui24gL1iZnchwX87Ox/+Xrm8HFmKlhmUO9n/QgTT+Nz1RGMEN1+HijvsoAhS0TS8XjSfzRkrwvK2pJQIDAQAB"
    };
    edit = window.light.page.products["payPassword"] = s.create(s.edit, options);
    s.edit.installed && edit.render();
    edit.available = true;
    return typeof cb === "function" ? cb(edit.getVersion()) : void 0;
  });
};

getPasswordControlInstalledText = function() {
  if (!isMac()) {
    return "已安装";
  }
  return "已安装 (版本号：" + macPasswordControlversion + ")\n<a href=\"https://download.alipay.com/sec/edit/wkaliedit.dmg\">下载最新版 Mac 安全控件</a>";
};

jsonp = function(url, cb) {
  return $.ajax({
    url: url,
    dataType: "jsonp",
    jsonp: "cb",
    success: cb
  });
};

getLDNS = function(cb) {
  return jsonp("http://140.205.130.1/api/cdnDetect?method=createDetect", function(data) {
    return jsonp(data.content, cb);
  });
};

getLiwushuoAPIInfo = function(cb) {
  var asset, assets;

  asset = "http://api.liwushuo.com/v2/items/1042917";
  return $.ajax({
    url: asset,
    dataType: "script",
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

getLiwushuoIndexInfo = function(cb) {
  var asset, assets;

  asset = "http://www.liwushuo.com";
  var _dataType = location.host === "www.liwushuo.com" ? "text" : "script";
  return $.ajax({
    url: asset,
    dataType: _dataType,
    success: function() {
      return cb(1);
    },
    error: function() {
      return cb(0);
    },
    timeout: 4000
  });
};

log("基础信息", {
  "tagname": "h3"
});

log("用户代理：<span id='user_agent'>" + (getUAString()) + "</span>");

log("系统信息：<span id='os_info'>" + (getOSInfo()) + "</span>");

log("浏览器信息：<span id='browser_info'>" + (getBrowserInfo()) + "</span>");

log("Flash 版本号：<span id='flash_version'>" + (getFlashVersion()) + "</span>");

log("Cookie 状态：<span id='cookie_enable'>" + (getCookieEnable() ? '开启' : '禁用') + "</span>");

log("JavaScript 状态：开启 (版本号：<span id='JS_VERSION'>" + JS_VERSION + "<span>)");

log("LocalStorage 状态：<span id='local_storage'>" + (store.enabled ? '开启' : '禁用') + "</span>");

log("网络信息", {
  "tagname": "h3"
});

log("图片 CDN：检测中...", "imgcdn");

getImageCDNInfo(function(success, total) {
  var msg;

  msg = success === total ? "成功" : "失败";
  return log("图片 CDN：<span id='pic_cdn_info'>连接" + msg + "，共尝试 " + total + " 张图片，其中 " + success + " 张解析正常</span>", {
    "id": "imgcdn",
    "type": "clear"
  });
});

log("礼物说 API：检测中...", {
  "id": "lwsapi"
});

getLiwushuoAPIInfo(function(success) {
  var msg;
  msg = success ? "成功" : "失败";
  return log("礼物说 API：<span id='lws_api'>连接" + msg + "</span>", {
    "id": "lwsapi",
    "type": "clear"
  });
});

log("礼物说首页：检测中...", {
  "id": "lwsindex",
  "type": "clear"
});

getLiwushuoIndexInfo(function(success) {
  var msg;

  msg = success ? "成功" : "失败";
  return log("礼物说首页：<span id='lws_home_page'>连接" + msg + "</span>", {
    "id": "lwsindex",
    "type": "clear"
  });
});

log("Assets CDN：检测中...", {
  "id": "assetscdn2"
});

getAssetsCDNInfo(function(success) {
  var msg;

  msg = success ? "成功" : "失败";
  return log("Assets CDN：<span id='cdn_info'>连接" + msg + "</span>", {
    "id": "assetscdn2",
    "type": "clear"
  });
});

log("Detail页面：检测中...", {
  "id": "detail"
});

getDetailInfo(function(success) {
  var msg;

  msg = success ? "成功" : "失败";
  return log("Detail页面：<span id='lws_detail_page'>连接" + msg + "</span>", {
    "id": "detail",
    "type": "clear"
  });
});

log("淘宝首页：检测中...", {
  "id": "taobaoindex"
});

getTaobaoInfo(function(success) {
  var msg;

  msg = success ? "成功" : "失败";
  return log("淘宝首页：<span id='taobao_home_page'>连接" + msg + "</span>", {
    "id": "taobaoindex",
    "type": "clear"
  });
});

log("阿里巴巴首页：检测中...", {
  "id": "alibabaindex"
});

getAlibabaInfo(function(success) {
  var msg;

  msg = success ? "成功" : "失败";
  return log("阿里巴巴首页：<span id='alibaba_home_page'>连接" + msg + "</span>", {
    "id": "alibabaindex",
    "type": "clear"
  });
});

log("新浪首页：检测中...", {
  "id": "sinaindex"
});

getSinaInfo(function(success) {
  var msg;

  msg = success ? "成功" : "失败";
  return log("新浪首页：<span id='zhalang_home_page'>连接" + msg + "</span>", {
    "id": "sinaindex",
    "type": "clear"
  });
});

log("腾讯首页：检测中...", {
  "id": "tencentindex"
});

getTencentInfo(function(success) {
  var msg;

  msg = success ? "成功" : "失败";
  return log("腾讯首页：<span id='qq_home_page'>连接" + msg + "</span>", {
    "id": "tencentindex",
    "type": "clear"
  });
});

log("Local DNS：检测中...", "ldns");

getLDNS(function(data) {
  var msg;

  msg = "" + data.content.ldns + "<br>本地IP：" + data.content.localIp + " (id: " + data.content.detectId + ")";
  $.getJSON("http://pubstatic.b0.upaiyun.com/?_upnode", function(json) {
    log("目标IP：<span id='local_target_ip'>" + json.addr + ' </span>(' + json.server + ')', "ldns");
    $('#ipinfo').css("cssText", 'display:block');
    $('#ipinfo').html('<p>客户端IP: <span id="client_ip">' + data.content.localIp + '</span><br/>DNS IP: <span id="dns_ip">' + data.content.ldns + '</span><br/>目标IP: <span id="target_ip">' + json.addr + ' </span>(' + json.server + ')</p>');
  });
  return log("Local DNS：<span id='local_dns_info'>" + msg + "</span>", {
    "id": "ldns",
    "type": "clear"
  });
});
function _reload(){
    if (getParam("a_sp_1") || getParam("client_ip")) {
    return;
  }

  var netStateData = {
    a_sp_1: $("#a_sp_1").text(),
    a_sp_2: $("#a_sp_2").text(),
    a_sp_3: $("#a_sp_3").text(),
    a_sp_baidu: $("#a_sp_baidu").text(),
    a_sp_taobao: $("#a_sp_taobao").text(),
    a_sp_qq: $("#a_sp_qq").text(),
    client_ip: $("#client_ip").text(),
    dns_ip: $("#dns_ip").text(),
    target_ip: $("#target_ip").text(),

    user_agent: $("#user_agent").text(),
    os_info: (getOSInfo()),
    browser_info: (getBrowserInfo()),
    flash_version: (getFlashVersion()),
    cookie_enable: (getCookieEnable() ? '开启' : '禁用'),
    local_storage: (store.enabled ? '开启' : '禁用'),
    pic_cdn_info: $("#pic_cdn_info").text(),
    lws_api: $("#lws_api").text(),
    lws_home_page: $("#lws_home_page").text(),
    JS_VERSION: $("#JS_VERSION").text(),
    cdn_info: $("#cdn_info").text(),
    lws_detail_page: $("#lws_detail_page").text(),
    taobao_home_page: $("#taobao_home_page").text(),
    zhalang_home_page: $("#zhalang_home_page").text(),
    alibaba_home_page: $("#alibaba_home_page").text(),
    qq_home_page: $("#qq_home_page").text(),
    local_target_ip: $("#local_target_ip").text(),
    local_dns_info: $("#local_dns_info").text()
  };

  window.location.href = decodeURIComponent(window.location.href + "?a_sp_1=" + netStateData.a_sp_1 +
    "&a_sp_2=" + netStateData.a_sp_2 +
    "&a_sp_3=" + netStateData.a_sp_3 +
    "&a_sp_baidu=" + netStateData.a_sp_baidu +
    "&a_sp_taobao=" + netStateData.a_sp_taobao +
    "&a_sp_qq=" + netStateData.a_sp_qq +
    "&client_ip=" + netStateData.client_ip +
    "&target_ip=" + netStateData.target_ip +
    "&dns_ip=" + netStateData.dns_ip +
    "&user_agent=" + navigator.userAgent +
    "&os_info=" + netStateData.os_info +
    "&browser_info=" + netStateData.browser_info +
    "&flash_version=" + netStateData.flash_version +
    "&cookie_enable=" + netStateData.cookie_enable +
    "&local_storage=" + netStateData.local_storage +
    "&pic_cdn_info=" + netStateData.pic_cdn_info +
    "&lws_api=" + netStateData.lws_api +
    "&lws_home_page=" + netStateData.lws_home_page +
    "&JS_VERSION=" + JS_VERSION +
    "&cdn_info=" + netStateData.cdn_info +
    "&lws_detail_page=" + netStateData.lws_detail_page +
    "&taobao_home_page=" + netStateData.taobao_home_page +
    "&zhalang_home_page=" + netStateData.zhalang_home_page +
    "&alibaba_home_page=" + netStateData.alibaba_home_page +
    "&qq_home_page=" + netStateData.qq_home_page +
    "&local_target_ip=" + netStateData.local_target_ip +
    "&local_dns_info=" + netStateData.local_dns_info);

}
var timer = setTimeout(function(){_reload()},5000)
window.onload = function() {
  clearTimeout(timer);
  _reload();
}
 
});