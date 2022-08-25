// JavaScript Document

/* 时间： 20180111
* 使用范围： 首页
* 说明： 首页检索词为“作者”时，检索框输入内容后显示下拉提示框，选中的内容会显示在提示框中
* */


/* 列表复选框事件，功能有
    * 1、选中/取消 input[type=checkbox]，
    * 2、选中复选框，数量不超过5，超出会有提示框
    * 3、鼠标移入，添加当前标记（背景色）
    * 4、折叠/展开列表层级
    **/
var that_dropBox;

if (!this.space1) {
    this.space1 = CreateCacheSpace(20, 0);
}

$(function () {
    new searchClassify();
});


function searchClassify(){

    this.dropBox = $(".droplist");
    this.inputBox = $(".search-form");
    // this.reopt = this.inputBox.find(".reopt");
    //this.btnClear = $(".btn-cleartext");
    this.textInput = this.inputBox.find(".search-input");
    this.submitBtn = this.inputBox.find(".btn-search");
    this.loaded = false;
    this.currentSelection = -1;
    this.init();
}


searchClassify.prototype = {
    init: function(){
        var that = this;

        that.inputFunc();//输入框事件
        that.dropLifunc();//下拉框条目移入事件
        //that.submit();//检索事件
        that.docClick();//鼠标点击时，判断是否关闭下拉列表框

        that.tabHistory();//切换 “浏览记录” 和 “检索历史”
    },

    dropLifunc: function(){ //鼠标移入层级 添加selected
        var that = this;
        that.dropBox.on("mouseenter", "dd", function () {
            if($(this).hasClass("selected")) return;
            that._addSelected(this);
            that.currentSelection = that._currentIndex(this);
        });
        that.dropBox.on("click", "dd", function () {
            that._addSelected(this);
            that.currentSelection = that._currentIndex(this);
            window.open($('.droplist .selected').attr('href'));
            that.textInput.val($(this).find("h6 b").text()).trigger("keyup");
            that._clearDropList();//隐藏下拉列表,恢复初始状态
        });
    },

    //清除所有selected,添加当前selected
    _addSelected : function(obj){
        var that = this;
        that.dropBox.find("dd").removeClass("selected");
        $(obj).addClass("selected");
    },

    //获取selected的索引
    _currentIndex : function(obj){
        return $(obj).closest(".list-hook").find("dd").index(obj);
    },

    inputFunc: function(){//输入框事件
        var that = this;

        var isCNInput = false;//中文输入标识
        //开始进行中文输入，设定中文输入标识
        $("body").on("compositionstart", that.inputText, function (event) {
            isCNInput = true;
        })
        //中文输入结束 修改标识，提供推荐
        $("body").on("compositionend", that.inputText, function () {
            isCNInput = false;
        })
        var debounce = function (fn, delay) {
            // 定时器，用来 setTimeout
            var timer;

            // 返回一个函数，这个函数会在一个时间区间结束后的 delay 毫秒时执行 fn 函数
            return function () {
                // 保存函数调用时的上下文和参数，传递给 fn
                var context = this
                var args = arguments
                clearTimeout(timer);

                timer = setTimeout(function () {
                    fn.apply(context, args)
                }, delay);
            }
        }
        $("body").on("input", that.inputText, debounce(function (event) {
            if (isCNInput) {
                //中文输入不截断
                return;
            }
        }, 300));

        that.inputBox.on("paste keyup","input[type=text]", function(event){
            //event.preventDefault();
            if (!isCNInput)
                if (event.which != 38 && event.which != 40)
                    that._judgeHook(this);//根据input值，判断下拉框推荐内容显示/隐藏、创建/移除
        })
            .bind("keydown",function(event) {
                switch(event.which) {
                    case 38: // Up arrow
                        event.preventDefault();
                        that.dropBox.find("dd").removeClass('selected');
                        if((that.currentSelection - 1) >= 0){
                            that.currentSelection--;
                        } else {
                            that.currentSelection = that.dropBox.find("dd").length - 1;
                        }
                        that.dropBox.find("dd:eq(" + that.currentSelection + ")" ).addClass('selected');
                        break;
                    case 40: // Down arrow
                        event.preventDefault();
                        that.dropBox.find("dd").removeClass('selected');
                        if((that.currentSelection + 1) < that.dropBox.find("dd").length){
                            that.currentSelection++;
                        } else {
                            that.currentSelection = 0;
                        }
                        that.dropBox.find("dd:eq(" + that.currentSelection + ")" ).addClass('selected');
                        break;
                    case 13: // Enter
                        event.preventDefault();
                        if( that.currentSelection == -1){//手动输入
                            if($.trim(that.textInput.val())){
                                that.textInput.trigger("keyup");
                            }else{ //search按钮
                                that.submitBtn.trigger("click");
                            }

                        }else if(that.currentSelection > -1){//选择列表
                            _val = that.dropBox.find("dd").eq(that.currentSelection).find("h6 b").text();
                            that.textInput.val(_val).trigger("keyup");
                        }
                        that._clearDropList();//隐藏下拉列表,恢复初始状态
                        break;
                    case 27: // Esc
                        that._clearDropList();//隐藏下拉列表,恢复初始状态
                        break;
                }
            });

        //input获取焦点
        that.inputBox.on("focus","input[type=text]", function (event) {
            event.preventDefault();
            that._judgeHook(this);//根据input值，判断下拉框推荐内容显示/隐藏、创建/移除
        });
    },



    _judgeHook: function(input){//判断下拉菜单是否显示
        var that = this;
        inputVal = $.trim($(input).val());//input当前值
        state = that.dropBox.find(".list-hook").attr("data-hook");//分为两种状态：history、matching

        if (state == undefined || (inputVal == "" && state == "matching") || (inputVal != "" && state == "history") || (inputVal != "" && state == "matching")) {
            _top = $(input).offset().top + $(input).outerHeight();
            _left = $(input).offset().left;
            that_dropBox = that.dropBox.css({
                "top": _top,
                "left": _left,
                "width": $(input).outerWidth()
            })
                .empty();
            var cachesu = space1.getExData($(".urtp_search_field").attr("value").toLowerCase() + $.trim($('.search-form .search-input').val()));
            cachesu ? that_dropBox.append(cachesu).show() : that._matchListFormat(inputVal);
        }

        that.loaded = true;
    },

    docClick: function () {
        var that = this;
        //点击空白处、添加、关闭按钮，清空下拉列表框内容
        $(document).on("click",function(event){
            $tar = $(event.target);
            if($tar.closest(that.textInput).length != 1 && $tar.closest(that.dropBox).length != 1 && $tar.closest(".popup-prompt").length != 1){
                if(that.loaded) {
                    //$(document).off("click");//阻止事件冒泡
                    that._clearDropList();//隐藏下拉列表,恢复初始状态
                    that.loaded = false;
                }
            }

        });
    },

    _clearDropList: function(){
        var that = this;
        that.dropBox.empty().hide();
    },

    //不同检索项对应下拉框不同的推荐列表格式
    _matchListFormat : function (val) {
        var that = this;
        switch(val){
            //无内容时，显示浏览历史和检索历史
            case "":
                //TODO 区分两个历史  分别存放到缓存数组
                head = '<div class="head-hook">\n' +
                    '        <a href="javascript:;" class="btn-tab-history cur">浏览记录</a>\n' +
                    '        <a href="javascript:;" class="btn-tab-history">历史记录</a>\n' +
                    '    </div>';
                //浏览历史
                historyList = '<dl class="list-hook" data-hook="history">\n';
                for (let i = 0; i < drop_browse.length; i++) {
                    historyList = historyList + drop_browse[i];
                }
                historyList = historyList + "</dl>";
                foot = '<div class="foot-hook">\n' +
                    '        <a href="javascript:_dropHistoryClear();" class="btn-clear-history">清空历史</a>\n' +
                    '    </div>';
                dropDetailContent = head + historyList + foot;
                that_dropBox.empty().append(dropDetailContent).show();
            //有内容时，（匹配相关项？？产品经理暂无说明）
            default:
                //推荐列表
                var searchfield = $('.urtp_search_field').val();
                var searchvalue = $('.search-form .search-input').val();
                if ($urtp.isNull(searchvalue)) {
                    return;
                } else if ($urtp.isNull($urtp.filterSpecialCodeForReference(searchvalue))) {
                    return;
                }
                searchvalue = $urtp.filterSpecialCodeForReference(searchvalue);
                if (searchvalue.length > 100) {
                    return;
                }
                $urtp.Observer.fire("_searchbox_word_keyup_event", {
                    "t": "total",
                    "f": searchfield,
                    "v": $urtp.filterSpecialCodeForReference(searchvalue)
                })
        }
    },


    //切换 “浏览记录” 和 “检索历史”
    tabHistory: function () {
        var that = this;
        that.dropBox.on("click",".head-hook a",function () {
            var historyText = '';

            $(this).addClass("cur").siblings().removeClass();
            _index = $(this).parent().find("a").index(this);
            historyText = '';
            switch(_index){
                case 0:
                    //浏览历史
                    for (let i = 0; i < drop_browse.length; i++) {
                        historyText = historyText + drop_browse[i];
                    }
                    break;
                case 1:
                    //检索历史
                    for (let i = 0; i < drop_history.length; i++) {
                        historyText = historyText + drop_history[i];
                    }
                    break;

            }
            that.dropBox.find(".list-hook").html(historyText);
            that.currentSelection = -1;
        });
    }
};

function _dropHistoryClear (){
    that_dropBox.empty().append(head + "<dl class=\"list-hook\" data-hook=\"history\"></dl>" + foot).show();
    drop_browse.length = 0;
    drop_history.length = 0;
}