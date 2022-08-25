(function (window) {

  var u = {}

  /**
   * 获取url条件参数中key值
   */
  u.getQueryString = function (key) {
    var url = document.location.toString();
    var arrObj = url.split('?');
    if (arrObj.length > 1) {
      var arrPara = arrObj[1].split('&');
      var arr;
      for (var i = 0; i < arrPara.length; i++) {
        arr = arrPara[i].split('=');
        if (arr != null && arr[0] == key) {
          return decodeURIComponent(arr[1]);
        }
      }
      return '';
    } else {
      return '';
    }
  }

  /**
   * u.getQueryString函数解析url中的查询条件时以?分割进行解析 条件值中带有?则会解析有误
   * 如 http://localhost/login?name=user1&v=*?%E6%B0%B4?*
   * 于是新增一种查找代替split的解析函数 根据需要选择使用
   */
  u.getUrlCondition = function (key) {
    var url = document.location.toString();
    if (url.indexOf('?') > -1) {
      var arrObj = [url.substring(0, url.indexOf('?')), url.substring(url.indexOf('?') + 1)];
      var condition = arrObj[1].split('&');
      var arr;
      for (var i = 0; i < condition.length; i++) {
        arr = condition[i].split('=');
        if (arr != null && arr[0] == key)
          return arr[1];
      }
      return '';
    } else {
      return '';
    }
  }

  //获取不带参数的url
  //以问号为边界，用正则表达式匹配替换问号右侧的所有参数及其对应值，并替换为空，得到不带参数的url
  u.getBaseUrl = function () {
    return location.href.replace(/\b\?.{1,}/, '');
  }

  //判断一个字段是否为''、null、undefined
  u.isEmpty = function (arg1) {
    if ((typeof arg1) == 'string') {
      arg1 = arg1.trim();
    }
    return !arg1 && arg1 !== 0 && typeof arg1 !== 'boolean' ? true : false;
  }

  u.isDate = function (arg) {
    var res = false;
    // if (isNaN(arg) && !isNaN(Date.parse(arg))) {
    if (!isNaN(Date.parse(arg))) {
      res = true;
    }
    return res;
  }

  u.getTodayDate = function () {
    return this.getDateWithHyphen(new Date());
  }

  u.getDateWithHyphen = function (d) {
    var year = d.getFullYear(); //得到年份
    var month = d.getMonth() + 1;//得到月份
    month = month < 10 ? '0' + month : month;
    var date = d.getDate();//得到日期
    date = date < 10 ? ('0' + date) : date;
    return year + '-' + month + '-' + date;
  }

  //最近一周
  u.getLastWeek = function () {
    var end = new Date();
    var start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
    var s = u.getDateWithHyphen(start);
    var e = u.getDateWithHyphen(end);
    return [s, e];
  }

  //最近一月
  u.getLastMonth = function () {
    var end = new Date();
    var start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
    var s = u.getDateWithHyphen(start);
    var e = u.getDateWithHyphen(end);
    return [s, e];
  }

  //最近半年
  u.getLastHalfYear = function () {
    var end = new Date();
    var start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 182);
    var s = u.getDateWithHyphen(start);
    var e = u.getDateWithHyphen(end);
    return [s, e];
  }

  //最近一年
  u.getLastYear = function () {
    var end = new Date()
    var start = new Date()
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 365)
    var s = u.getDateWithHyphen(start)
    var e = u.getDateWithHyphen(end)
    return [s, e]
  }
  //最近三年
  u.getLastthreeYear = function () {
    var end = new Date()
    var start = new Date()
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 365 * 3)
    var s = u.getDateWithHyphen(start)
    var e = u.getDateWithHyphen(end)
    return [s, e]
  }

  //今年迄今
  u.getYearSoFar = function () {
    var end = new Date()
    var year = (new Date()).getFullYear();
    var start = new Date(year + '-01-01');
    var s = u.getDateWithHyphen(start)
    var e = u.getDateWithHyphen(end)
    return [s, e]
  }

  //去年一年
  u.getPreYear = function () {
    var year = (new Date()).getFullYear();
    var end = new Date((year - 1) + '-12-31')
    var start = new Date((year - 1) + '-01-01');
    var s = u.getDateWithHyphen(start)
    var e = u.getDateWithHyphen(end)
    return [s, e]
  }

  /**
   * 字符串格式化方法
   * @author wangwei
   * @since 2020/08/26
   */
  String.prototype.format = function () {
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,
        function (m, i) {
          return args[i];
        });
  }

  /**
   * 字符串删掉首尾的任意字符
   * @author wangwei
   * 示例删除结尾的逗号:
   * var str='1,2,';
   * str.trim(',','right')=====1,2
   * @since 2020/08/28
   */
  String.prototype.trim = function (char, type) {
    if (char) {
      if (type == 'left') {
        return this.replace(new RegExp('^\\' + char + '+', 'g'), '');
      } else if (type == 'right') {
        return this.replace(new RegExp('\\' + char + '+$', 'g'), '');
      }
      return this.replace(new RegExp('^\\' + char + '+|\\' + char + '+$', 'g'), '');
    }
    return this.replace(/^\s+|\s+$/g, '');
  };

  //或者使用正则表达式，方法如下
  String.prototype.startsWith = function (str) {
    var reg = new RegExp('^' + str);
    return reg.test(this);
  }

  //或者使用正则表达式
  String.prototype.endWith = function (str) {
    var reg = new RegExp(str + '$');
    return reg.test(this);
  }

  // Production steps of ECMA-262, Edition 5, 15.4.4.18
  // Reference: https://es5.github.io/#x15.4.4.18
  if (!Array.prototype['forEach']) {
    Array.prototype.forEach = function (callback, thisArg) {

      if (this == null) {
        throw new TypeError('Array.prototype.forEach called on null or undefined');
      }

      var T, k;
      // 1. Let O be the result of calling toObject() passing the
      // |this| value as the argument.
      var O = Object(this);

      // 2. Let lenValue be the result of calling the Get() internal
      // method of O with the argument 'length'.
      // 3. Let len be toUint32(lenValue).
      var len = O.length >>> 0;

      // 4. If isCallable(callback) is false, throw a TypeError exception.
      // See: https://es5.github.com/#x9.11
      if (typeof callback !== 'function') {
        throw new TypeError(callback + ' is not a function');
      }

      // 5. If thisArg was supplied, let T be thisArg; else let
      // T be undefined.
      if (arguments.length > 1) {
        T = thisArg;
      }

      // 6. Let k be 0
      k = 0;

      // 7. Repeat, while k < len
      while (k < len) {

        var kValue;

        // a. Let Pk be ToString(k).
        //    This is implicit for LHS operands of the in operator
        // b. Let kPresent be the result of calling the HasProperty
        //    internal method of O with argument Pk.
        //    This step can be combined with c
        // c. If kPresent is true, then
        if (k in O) {

          // i. Let kValue be the result of calling the Get internal
          // method of O with argument Pk.
          kValue = O[k];

          // ii. Call the Call internal method of callback with T as
          // the this value and argument list containing kValue, k, and O.
          callback.call(T, kValue, k, O);
        }
        // d. Increase k by 1.
        k++;
      }
      // 8. return undefined
    };
  }

  if (!Array.prototype.filter) {
    Array.prototype.filter = function (func, thisArg) {
      'use strict';
      if (!((typeof func === 'Function' || typeof func === 'function') && this))
        throw new TypeError();

      var len = this.length >>> 0,
          res = new Array(len), // preallocate array
          t = this, c = 0, i = -1;
      if (thisArg === undefined) {
        while (++i !== len) {
          // checks to see if the key was set
          if (i in this) {
            if (func(t[i], i, t)) {
              res[c++] = t[i];
            }
          }
        }
      } else {
        while (++i !== len) {
          // checks to see if the key was set
          if (i in this) {
            if (func.call(thisArg, t[i], i, t)) {
              res[c++] = t[i];
            }
          }
        }
      }

      res.length = c; // shrink down array to proper size
      return res;
    };
  }

  /**
   * regist:  注册事件,同一类型可注册多个事件
   *  var a = function(){console.log("我是a函数")}
   *  window.$urtp.Observer.regist('t1',a);
   *  window.$urtp.Observer.regist('t1',function(){console.log("我是匿名函数")});
   * fire:    触发事件
   *  window.$urtp.Observer.fire('t1',[参数]);
   * remove:  移除事件
   *  window.$urtp.Observer.remove('t1');     移除全部
   *  window.$urtp.Observer.remove('t1',a);   移除指定某个事件,若有此需求注册时禁止使用匿名函数
   */
  u.Observer = (function () {
    var __messages = {};
    return {
      regist: function (type, fn) {
        if (typeof __messages[type] === 'undefined') {
          __messages[type] = [fn]
        } else {
          __messages[type].push(fn);
        }
      },
      fire: function (type, args) {
        if (!__messages[type])
          return;
        var events = {
              type: type,
              args: args || {}
            },
            i = 0,
            len = __messages[type].length;
        for (; i < len; i++) {
          __messages[type][i].call(this, events);
        }
      },
      remove: function (type, fn) {
        if (__messages[type] instanceof Array) {
          if(typeof fn == 'undefined') {
            __messages[type] = [];
            return;
          }
          var i = __messages[type].length - 1;
          for (; i >= 0; i--) {
            __messages[type][i] === fn && __messages[type].splice(i, 1)
          }
        }
      }
    }
  })();

  //多级树节点降维，减小复杂度
  u.compileFlatState = function (nt) {
    var keyCounter = 0;
    var childrenKey = 'children';
    var flatTree = []; //扁平树
    var thisFlatTree = this.flatTree;

    function flattenChildren(node, parent) {
      //扁平化子节点
      node.nodeKey = keyCounter++;
      flatTree[node.nodeKey] = {
        node: node,
        nodeKey: node.nodeKey,
        checked: false
      };
      if (typeof parent != 'undefined') {
        flatTree[node.nodeKey].parent = parent.nodeKey;
        flatTree[parent.nodeKey][childrenKey].push(node.nodeKey);
      }
      if (node[childrenKey]) {
        flatTree[node.nodeKey][childrenKey] = [];
        //node[childrenKey].forEach(child => flattenChildren(child, node));
        node[childrenKey].forEach(function (child) {
          return flattenChildren(child, node);
        });
      }
    }

    nt.forEach(function (rootNode) {
      flattenChildren(rootNode);
    });
    return flatTree;
  }
  //初始化翻页控件（_pagelist.html）
  u.initPageControl = function () {
    layui.use(['laypage'], function () {
      var laypage = layui.laypage;
      laypage.render({
        elem: 'pager'
        , count: config.total
        , curr: config.pindex
        , limit: config.psize
        , layout: ['count', 'prev', 'page', 'next', 'limit'] //, 'refresh', 'skip'
        , jump: function (obj, first) {
          config.pindex = parseInt(obj.curr); //索引
          config.psize = parseInt(obj.limit);//长度
          if (!first) {
            search();
          }
        }
      });
    })
  };

  /**
   * @description 工具书目录分页事件
   * @author chenhao
   */
  u.initPageControlCRFD = function () {
    layui.use(['laypage'], function () {
      var laypage = layui.laypage;
      laypage.render({
        elem: 'pager'
        , count: config.total
        , curr: config.pindex
        , limit: config.psize
        , layout: ['count', 'prev', 'page', 'next'] //, 'refresh', 'skip'
        , jump: function (obj, first) {
          config.pindex = parseInt(obj.curr); //索引
          config.psize = parseInt(obj.limit);//长度
          if (!first) {
            var tempBookid = $('#thisSourceCode').val();
            if (!$urtp.isNull(tempBookid)) {
              getCRFDIndexes(tempBookid, 'ZW');
            }
          }
        }
      });
    })
  };

  /**
   * @description 复制文本内容到剪贴板
   * @author chenhao
   * @since 2020/9/9
   * @param value 复制内容
   */
  u.copyUrl = function (value) {
    var oInput = document.createElement('input');
    oInput.value = value;
    document.body.appendChild(oInput);
    oInput.select();
    document.execCommand('Copy');
    oInput.remove();
  };

  u.encodeMoreOnce = function (value, count) {
    if (value != null && count > 0) {
      for (var i = 0; i < count; i++) {
        value = encodeURIComponent(value);
      }
    }
    return value;
  };

  u.encodeTwice = function (value) {
    return this.encodeMoreOnce(value, 2);
  };
  /**
   * url解码
   * @param count 次数
   */
  u.decodeMoreOnce = function (value, count) {
    if (value != null && count > 0) {
      for (var i = 0; i < count; i++) {
        value = decodeURIComponent(value);
      }
    }
    return value;
  };
  /**
   * url两次解码
   */
  u.decodeTwice = function (value) {
    return this.decodeMoreOnce(value, 2);
  };

  u.keyDESDefault = '1qaz1qaz';
  /**
   * DES加密
   */
  u.encryptByDES = function (message, key) {
    if (typeof key != 'string' || key.length < 8) {
      key = u.keyDESDefault;
    }
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.ciphertext.toString();
  }

  /**
   * DES解密
   */
  u.decryptByDES = function (ecryptStr, key) {
    if (typeof key != 'string' || key.length < 8) {
      key = u.keyDESDefault;
    }
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var decrypted = CryptoJS.DES.decrypt({
      ciphertext: CryptoJS.enc.Hex.parse(ecryptStr)
    }, keyHex, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    });
    var result_value = decrypted.toString(CryptoJS.enc.Utf8);
    return result_value;
  }

  /**
   * @description 文本框+按钮，按钮单击，文本框回车触发同样的交互
   * @param btn   按钮的选择器
   * @param input 文本框的选择器
   * @param callback 触发的回调函数
   */
  u.clickOrEnter = function (btn, input, callback) {
    $(btn).unbind('click').bind('click', function () {
          callback();
        }
    )
    var kp = function (ele) {
      $(ele).unbind('keyup').bind('keyup', function (e) {
        if (e.keyCode == 13) {
          callback();
        }
      })
    }
    if (input instanceof Array) {
      for (var i in input) {
        kp(i);
      }
    } else {
      kp(input)
    }


  }

  u.ajaxDocument2 = function (option) {
    /**
     * option：配置项
     *  {
     *     url,地址
     *     type, 请求类型POST，GET
     *     header，请求头token等设置
     *     param 参数
     *     success 成功后的回调
     *     error 错误回调
     *  }
     */
    if (!option) {
      throw 'param is error or NULL!';
    }
    if (!option.url) {
      throw 'url is NULL or length=0!';
    }
    if (!option.type) {
      throw 'type is NULL!';
    }
    var handler = function () {
      if (4 !== this.readyState) {
        return false;
      }
      if (option.complete) {
        option.complete(this.response);
      }
      if (200 === this.status) {
        if (option.success) {
          option.success(this.response);
        }
      } else {
        if (option.error) {
          option.error(new Error(this.statusText));
        }
      }
    };
    var client = new XMLHttpRequest();
    client.open(option.type, option.url, true);
    client.onreadystatechange = handler;
    client.responseType = 'blob';

    if (option.header) {
      for (var key in option.header) {
        client.setRequestHeader(key, option.header[key]);
      }
    }
    client.send(option.param);
  };

  u.parseStringToInt = function (t, defaultVal) {
    return this.isEmpty(t) ? (!this.isEmpty(defaultVal) ? defaultVal : 0) : parseInt(t);
  }

  u.specialCode = function (param) {
    return /[\\<>*]/.test(param)
  }

  u.filterSpecialCode = function (param) {
    var tmp = '';
    if (!this.isNull(param)) {
      //tmp = param.replace(/[#&!@$%^*<>()（）{}【】,，.。/[]]*/g, '');
      tmp = param.replace(/[#&!@$%^*<>()（）{}【】,，。[]]*/g, ''); //去掉英文半角句点，ISBN中有点分隔符，去掉/，因为CN中有反斜杠
      if (!this.isNull(tmp)) tmp = tmp.trim(' ');
    }
    return tmp;
  }

  //工具书详情页刊内检索
  u.filterSpecialCodeForCRFD = function (param) {
    var tmp = '';
    if (!this.isNull(param)) {
      tmp = param.replace(/[#&!@$%^*<>()（）{}【】,，.。/[]]*/g, '');
      if (!this.isNull(tmp)) tmp = tmp.trim(' ');
    }
    return tmp;
  }

  u.filterSpecialCodeForSearch = function (param) {
    var tmp = '';
    if (!this.isNull(param)) {
      /*
      * 去掉英文半角句点，ISBN中有点分隔符，去掉/，因为CN中有反斜杠
      * 去掉$，词频中需要$符号
      *  **/
      tmp = param.replace(/[#&!@%^*<>()（）{}【】,，。[]]*/g, '');
      if (!this.isNull(tmp)) tmp = tmp.trim(' ');
    }
    return tmp;
  }

  /*检索字段需要，不替换/.&() []这些 */
  u.filterSpecialCodefornavi = function (param) {
    var tmp = '';
    if (!this.isNull(param)) {
      tmp = param.replace(/[#!@$%^*<>（）{}【】,，。]*/g, '');
      if (!this.isNull(tmp)) tmp = tmp.trim(' ');
    }
    return tmp;
  }

  /*
  * 工具书需要支持全角、半角的星号和问号
  * */
  u.filterSpecialCodeForReference = function (param) {
    var tmp = '';
    if (!this.isNull(param)) {
      //tmp = param.replace(/[#&!@$%^*<>()（）{}【】,，.。/[]]*/g, '');
      tmp = param.replace(/[#&!@$%^<>()（）{}【】,，。/[]]/g, ''); //去掉英文半角句点，ISBN中有点分隔符
      if (!this.isNull(tmp)) tmp = tmp.trim(' ');
    }
    return tmp;
  }

  u.haveSpecialCodeForNavi = function (param) {
    return (/[#!@$%^*<>{}【】,，。[]]*/g).test(param);
  }

  /**
   * @description 绑定一框检索资源切换事件（下拉列表实时更新）；注：调用的页面需要自己确定检索事件触发时传入的resid（资源id）
   * @author chenhao
   * @since 2021/1/18
   */
  u.bindSearchboxLineSrchEvent = function () {
    $('.resultsrch .dblist_content button').off('click').on('click', function () {
      if (!$(this).hasClass('layui-btn-normal')) {
        //更新按钮状态
        $(this).siblings('.layui-btn-normal').removeClass('layui-btn-normal').addClass('layui-btn-primary');
        $(this).removeClass('layui-btn-primary').addClass('layui-btn-normal');
        //更新资源类型
        var search_type = $(this).attr('urtp-data-resid');

        //更新下拉框中的条件
        var url = urlBase + 'index/getSelectConditionView' + '?sysid=' + sysId + '&viewName=_searchbox_line_srch&resid=' + search_type;

        $.ajax({
          type: 'POST',
          url: url,
          contentType: 'application/json;charset=UTF-8',
          success: function (data) {
            $('.subheader-topline #searchbox_line_srch_templatePath').html(data);
          },
          error: function (XMLHttpRequest, errorType) {
            layer.msg('发生错误，请联系系统管理员。错误信息:' + XMLHttpRequest.responseText + '；错误类型：' + errorType);
          }
        });
      }
    });
  }

  /**
   * @description 数组去除重复元素（ES5）（不区分英文大小写，同一字母大小写形式视为同一个字符）
   * @param array 被执行数组
   * @author chenhao
   * @since 2021/3/9
   */
  u.distinct = function (array) {
    for (var i = 0; i < array.length; i++) {
      for (var j = i + 1; j < array.length; j++) {
        if (array[i].toUpperCase() == array[j].toUpperCase()) {//内层循环依次对比外层循环当前元素，若相等则删除当前索引处元素
          array.splice(j, 1);
          j--;
        }
      }
    }
    return array;
  }

  /**
   * @description 判断字符串中是否包含空格（检索框可能需要此需求）
   * @param stringParam 参数字符串
   * @author chenhao
   * @since 2021/4/25
   */
  u.hasSpace = function (stringParam) {
    if (stringParam.indexOf(' ') != -1) {
      alert('不能包括空格');
    }
  }

  u.extend = function () {
    var e = 1,
        t = arguments,
        n = function (e, t) {
          e = e || (t.constructor === Array ? [] : {});
          for (var a in t)
            e[a] = t[a] && t[a].constructor === Object ? n(e[a], t[a]) : t[a];
          return e
        };
    for (t[0] = 'object' == typeof t[0] ? t[0] : {}; e < t.length; e++)
      'object' == typeof t[e] && n(t[0], t[e]);
    return t[0]
  };

  //修改layui的下拉框的选中状态，这个应该不是正经的好办法
  u.setOption = function (sel, defaultVal) {
    var inputText = '';
    if (!$urtp.isEmpty(defaultVal)) {
      sel.find('option').each(function (i) {
        var val = $(this).val();
        if (val == defaultVal) {
          $(this).attr('selected', true);
          inputText = $(this).html();
        } else {
          $(this).attr('selected', false)
        }
      })
      sel.attr('urtp-data-val', defaultVal)
    } else {
      var val = '';
      sel.find('option').each(function (i) {
        if (i == 0) {
          $(this).attr('selected', true);
          val = $(this).val();
          inputText = $(this).html();
        } else {
          $(this).attr('selected', false)
        }
      })
      sel.attr('urtp-data-val', val)
    }
    //修改显示名称
    sel.siblings('div.layui-form-select').find('input').val(inputText);
  }


  //专业检索验证输入是否合法：请输入正确检索表达式
  u.validExpertSearch = function(d) {
    var c = /[><=%]/ig;
    if (!c.test(d)) {
      return false
    }
    var e = /\'\s(or|and)\s\'/i;
    if (e.test(d)) {
      return false
    }
    return true
  }

  //公共api
  window.$t = u;

})(window);

(function ($) {
  $.fn.extend({
    insertAtCaret: function (myValue, id) {
      var $t = $(this)[0];
      if (document.selection) {
        this.focus();
        // sel = document.selection.createRange();    //document。selection
        // sel.text = myValue;
        var text = $($(this).selector).val();
        $($(this).selector).val(text + myValue);
        this.focus();
      } else if ($t.selectionStart || $t.selectionStart == '0') {
        var startPos = $t.selectionStart;
        var endPos = $t.selectionEnd;
        var scrollTop = $t.scrollTop;
        $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
        this.focus();
        $t.selectionStart = startPos + myValue.length;
        $t.selectionEnd = startPos + myValue.length;
        $t.scrollTop = scrollTop;
      } else {
        this.value += myValue;
        this.focus();
      }
    }
  })
})(jQuery);