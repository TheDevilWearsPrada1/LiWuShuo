define('liwushuo/settings/cdn-diagnosis-test-img-first', function(require, exports, module) {
  var _nt = new Date();
  var _oi = 1;
  var _ok = true;
  var getParam = function(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
      results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
  };

  if (getParam("a_sp_1") || getParam("client_ip")) {
    document.getElementById("a_sp_1").innerHTML = getParam("a_sp_1")
    document.getElementById("a_sp_2").innerHTML = getParam("a_sp_2")
    document.getElementById("a_sp_3").innerHTML = getParam("a_sp_3")
    document.getElementById("a_sp_baidu").innerHTML = getParam("a_sp_baidu")
    document.getElementById("a_sp_taobao").innerHTML = getParam("a_sp_taobao")
    document.getElementById("a_sp_qq").innerHTML = getParam("a_sp_qq")
    document.getElementById("client_ip").innerHTML = getParam("client_ip")
    document.getElementById("dns_ip").innerHTML = getParam("dns_ip")
    document.getElementById("target_ip").innerHTML = getParam("target_ip")
    return;
  }

  function test_bd() {
    _nt = new Date();
    var d = document.createElement('img');
    d.src = 'http://7.su.bdimg.com/skin/21.jpg?' + (new Date()) + Math.random();
    d.onerror = d.onload = function(e) {
      if (this.width == 1600) {
        var s = parseInt(174398 / 1024 / ((new Date() - _nt) / 1000) * 100) / 100;
                if (e.type == 'error') {
          s = 0;
        }
        document.getElementById('a_sp_baidu').innerHTML = s;
      }
      test_tb();
    };
    d.style.display = 'none';
    document.getElementById('a_sp_baidu').parentNode.appendChild(d);
  }

  function test_tb() {
    _nt = new Date();
    var d = document.createElement('img');
    d.src = 'http://img04.taobaocdn.com/imgextra/i4/19242040256480487/T2O6NIXXVdXXXXXXXX_!!1763599242-0-aitao.jpg?' + (new Date()) + Math.random();
    d.onerror = d.onload = function(e) {
      if (this.width == 230) {
        var s = parseInt(45726 / 1024 / ((new Date() - _nt) / 1000) * 100) / 100;
        if (e.type == 'error') {
          s = 0;
        }
        document.getElementById('a_sp_taobao').innerHTML = s;
      }
      test_tx();
    };
    d.style.display = 'none';
    document.getElementById('a_sp_taobao').parentNode.appendChild(d);
  }

  function test_tx() {
    _nt = new Date();
    var d = document.createElement('img');
    d.src = 'http://img1.gtimg.com/news/pics/hv1/111/189/1772/115272606.jpg?' + (new Date()) + Math.random();
    d.onerror = d.onload = function(e) {
      if (this.width == 940) {
        var s = parseInt(108467 / 1024 / ((new Date() - _nt) / 1000) * 100) / 100;
        if (e.type == 'error') {
          s = 0;
        }
        document.getElementById('a_sp_qq').innerHTML = s;
      }
    };
    d.style.display = 'none';
    document.getElementById('a_sp_qq').parentNode.appendChild(d);
  }

  function sw(i, s) {
    if (s < 1) _oi = s;
    if (i == 3) document.getElementById(_oi == 1 ? 'ok' : 'slow').style.display = 'block';
    if (i == 3 && !_ok) document.getElementById('error').style.display = 'block';
    if (i == 3) test_bd();
  }

  function test_sp(i) {
    _nt = new Date();
    var id = 'a_sp_' + i;
    var d = document.createElement('img');
    d.src = 'http://pubstatic.b0.upaiyun.com/check2.jpg?' + (new Date()) + Math.random();
    d.onerror = d.onload = function(e) {
      if (this.width != 900) _ok = false;
      var s = parseInt(236364 / 1024 / ((new Date() - _nt) / 1000) * 100) / 100;
      if (e.type == 'error') {
        s = 0;
      }
      document.getElementById(id).innerHTML = s;
      sw(i, s > 200);
      if (i < 3) test_sp(i + 1);
    };
    d.style.display = 'none';
    document.getElementById(id).parentNode.appendChild(d);
  }
  test_sp(1);

});