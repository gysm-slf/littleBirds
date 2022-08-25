var mainViewName = "_resultsrch_content";//加载单篇文献细览页主内容
var config = {}, memo = {}, gary = [], min = 3, max = 7;
config['joinAry'] = ['joinReferItem'];

function loadResultContent() {
    $('.banner').removeClass('banner').addClass('sub-banner');
    config = $urtp.search.ready(config).config;
    $.ajax({
        async: true,
        type: "POST",
        //url: urlBase + "resultsrch/maincontent",
        url: urlBase + "result/maincontent",
        data: {
            "sysid": sysId,
            "resid": config.resid,
            "tableid": config.tableid,
            "viewname": mainViewName
        },
        success: function (data) {
            $("#dictdate").html(data);
            bind();
            $urtp.Observer.fire('resultsrch-maincontent-loaded-event', {config: config})
        }
    });
}

function bind() {

    $urtp.Observer.regist("_searchbox_line_srch_event", function (data) {
        if (!searchValid()) return;
        setRange()
        search('trigger:search')
        grouper();
    })

    $urtp.Observer.regist("_searchbox_word_keyup_event", function (data) {
        setRange('all');
        // 为了解决异步查询响应时间相差较大而导致的缓存错乱问题 发起请求时首先应该获取缓存key
        var key = $(".urtp_search_field").attr("value").toLowerCase() + $.trim($('.search-form .search-input').val());
        var readyQuery = {curResultSearchCnt: 0};
        var queryModel = $urtp.search.ready(readyQuery).queryModelFactory(config.joinAry);
        queryModel = $urtp.cls.clipQueryModelFromClsHidden(queryModel);
        wordsConvert(queryModel);
        //实时推荐  仅显示前6条
        var start = $urtp.parseStringToInt(0);
        var length = $urtp.parseStringToInt(6);
        queryModel.setStart(start);
        queryModel.setLength(length);
        queryModel.setOrder('NUM_CHAR_H', 'asc', 'SEQUENCE', 'asc');
        $.ajax({
            async: true,
            type: "POST",
            contentType: "application/json",
            url: urlBase + "result/data?stype=" + config.stype + "&sysid=" + config.sysID + "&resid=" + (!!$("#scdb_dict_resId").val() ? $("#scdb_dict_resId").val() : config.resid),
            data: JSON.stringify(queryModel.toJson()),
            success: function (data) {
                //动态推荐内容生成
                if (data.indexOf("nodata") == -1) {
                    data = recommenderFormat(data);
                    space1.pushSpace(key, data);
                    var cache = space1.getExData($(".urtp_search_field").attr("value").toLowerCase() + $.trim($('.search-form .search-input').val()));
                    cache ? that_dropBox.empty().append(cache + '<dl class="list-hook" data-hook="matching"></dl>').show() : "";
                } else {
                    dropDetailContent = "";
                }
            },
            complete: function () {
                setRange();
            }
        });
    })

    $urtp.clickOrEnter('.btn-search', '.search-form .search-input', function () {

        setRange();
        if (!searchValid()) return;
        var searchfield = $('.urtp_search_field').val();
        var searchvalue = $('.search-form .search-input').val();

        if(drop_history.length == history_n) drop_history.pop();
        drop_history.unshift($(this)[0].outerHTML);

        //执行词典搜索
        $urtp.Observer.fire("_searchbox_line_srch_event", {
            "t": $('.option-list .all').hasClass('selected') ? 'total' : $('#tableid').val(),
            "f": searchfield,
            "v": $urtp.filterSpecialCodeForReference(searchvalue)
        })
    })
    $('.urtp_group_cluster').each(function () {
        gary.push($(this).attr('urtp-data-field'))
    })
    //切换检索方式，词义/词目。
    $(".search-menu li").click(function () {
        if (!!$('#dicgroup [cou="total"]').attr("data")) {
            $('#tableid').val($('#dicgroup [cou="total"]').attr("data"));
        }
        $(this).addClass("on").siblings().removeClass("on");
        var thisValue = $(this).attr("data");
        if (thisValue.toUpperCase() == "Wordmeaning".toUpperCase()) {
            $(".urtp_search_field").attr("value", "vs2");
            $(".urtp_search_field").attr("urtp-data-val", "vs2");
            $(".searchmain .btn-wildcard").hide();
        } else {
            $(".urtp_search_field").attr("value", "vs1");
            $(".urtp_search_field").attr("urtp-data-val", "vs1");
            $(".searchmain .btn-wildcard").show();
        }
        $(".search-menu li").attr("style", "color: #f6f6f6;")
        $(".search-menu .on").attr("style", "color: #fe0;")
        $('#dicpageindex').val(1);
        setRange();
        search();
        grouper();
    });

    $('.btn-wildcard').click(function () {
        var txt = $(this).text().trim();
        var pre = $('.search-input').val();
        $('.search-input').val(pre.substring(0, searchStart) + txt + pre.substring(searchEnd));
        searchStart++;
        searchStart == searchEnd ? searchEnd++ : searchEnd = searchStart;

        $('.search-input').focus();
        $('.search-input')[0].selectionStart = searchStart;
        $('.search-input')[0].selectionEnd = searchEnd;
    });
}

//词目检索若实际输入词不含通配符应在末尾添加*
function wordsConvert(queryModel) {
    var temp = queryModel.toJson();
    if (!!temp.searchQuery[0] && !!temp.searchQuery[0].item[0]) {
        var searchfield = temp.searchQuery[0].item[0].key;
        var searchvalue = temp.searchQuery[0].item[0].value;
    } else return;
    //词目检索若实际输入词不含通配符应在末尾添加*
    if (searchfield == "vs1") {
        if (searchvalue.indexOf("?") == -1 && searchvalue.indexOf("*") == -1) {
            temp.searchQuery[0].item[0].value = searchvalue.concat("*");
        }
    }
}

/**
 * 检索前规则校验
 * @returns {boolean}
 */
function searchValid() {
    var searchvalue = $('.search-form .search-input').val();
    if ($urtp.isNull(searchvalue)) {
        alert('请输入检索词');
        return false;
    } else if ($urtp.isNull($urtp.filterSpecialCodeForReference(searchvalue)) || searchvalue == '*') {
        alert('请输入有效的检索内容')
        return false;
    }
    searchvalue = $urtp.filterSpecialCodeForReference(searchvalue);
    if (searchvalue.length > 100) {
        alert('检索词最大长度为100个字符')
        return false;
    }
    if ($('#tableid').val().length == 0){
        alert('检索范围无效,请选择至少一种词典类型')
        return false;
    }
    return true;
}

function search() {
    var readyQuery = {curResultSearchCnt: 0};
    var triggerSearch = arguments.length > 0 && arguments[0] === 'trigger:search';

    var queryModel = triggerSearch ?
        $urtp.search.ready(readyQuery).queryModelFactory(config.joinAry) :
        $urtp.search.ready(readyQuery).queryModelFactory();
    queryModel = $urtp.cls.clipQueryModelFromClsHidden(queryModel);
    //TODO  排序还需调整
    queryModel.setOrder('NUM_CHAR_H', 'asc', 'SEQUENCE', 'asc');
    var start = $urtp.parseStringToInt(($("#dicpageindex").val() - 1) * 10, 0);
    var length = $urtp.parseStringToInt($('#dicpagesize').val(), 10);
    queryModel.setStart(start);//从第0条开始
    queryModel.setLength(length); //每页20条记录
    wordsConvert(queryModel);
    $.ajax({
        async: true,
        type: "POST",
        contentType: "application/json",
        url: urlBase + "result/data?stype=" + config.stype + "&sysid=" + config.sysID + "&resid=" + (!!$("#scdb_dict_resId").val() ? $("#scdb_dict_resId").val() : config.resid),
        data: JSON.stringify(queryModel.toJson()),
        beforeSend: function () {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.load();
            });
        },
        success: function (data) {
            $("#dictdate").html(data);
            if ($urtp.parseStringToInt($('#dictotalcount').val()) > 0) {
                $("#dicpages").show();
            } else {
                //展示无数据结果页面
                $("#dicpages").hide();
            }
        },
        complete: function () {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.closeAll('loading');
            });
        }
    });
}

//获取相关数据表, 可使用于获得tableId与tableName映射
//三种方式获取相关productDataTable 1.sysId + dataType + tableName 2.sysId + tableName + resourceId 3.sysId
function tableMap(ele, args) {
    $.ajax({
        async: false,
        type: "POST",
        contentType: "application/json",
        url: urlBase + "result/tablemap?" + args,
        success: function (data) {
            if (!!data) {
                $(ele).html(data);
            }
        }
    });
}

function grouper() {
    var readyQuery = {curResultSearchCnt: 0};
    var triggerSearch = arguments.length > 0 && arguments[0] === 'trigger:search';

    var queryModel = triggerSearch ?
        $urtp.search.ready(readyQuery).queryModelFactory(config.joinAry) :
        $urtp.search.ready(readyQuery).queryModelFactory();
    queryModel = $urtp.cls.clipQueryModelFromClsHidden(queryModel)
    wordsConvert(queryModel);

    $.ajax({
        async: true,
        type: "POST",
        contentType: "application/json",
        url: urlBase + "result/getSubViewCount",
        data: JSON.stringify(queryModel.toJson()),
        beforeSend: function () {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.load();
            });
        },
        success: function (data) {
            var dataList, elementCode;
            $('#dicgroup').html('');
            if(!!data && data.code == 200){
                if(data.dataList.length > 0){
                    dataList = data.dataList;
                    var map = [];
                    $('.option-list li').each(function (){
                        map.push({'k' : $(this).attr('urtp-data'), 'v' : $(this).attr('urtp-data-name'), 'n' : $(this).find('span').text(), 'f' : false});
                    })
                    for (let i = 0; i < dataList.length; i++)
                        for (let j = 0; j < map.length; j++) {
                            if(!!dataList[i][map[j]['k']])
                                map[j]['f'] = true;
                        }
                    elementCode = '<ul class="tabs">\n'
                        + '<li class="cur total">全部</li>\n';
                    for (let i = 0; i < map.length; i++) {
                        if(map[i]['f'])
                            // elementCode += '<li class="selected" urtp-data="' + dataList[i]['k'] + '" urtp-data-name="' + dataList[i]['v'] + '"><i></i><span>' + dataList[i]['n'] + '</span></li>';
                            elementCode += '<li urtp-data="' + map[i]['k'] + '">' + map[i]['n'] + '</li>\n';
                    }
                    elementCode += '</ul>'
                    $('#dicgroup').html(elementCode);

                    $('#dicgroup li').click(function (){
                        $(this).addClass('cur').siblings().removeClass('cur');
                        $('#dicgroup .total').hasClass('cur') ?  setRange('all') : $('#tableid').val($(this).attr('urtp-data'));

                        $('#dicpageindex').val(1);
                        $('#dicpagesize').val(10);
                        search();
                    })
                }
            }
        },
        complete: function () {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.closeAll('loading');
            });
        }
    });
}

//检索范围条件框是否自动展开   true:展开
function searchRangeInit(state){
    if(state){
        $('.btn-all').toggle();
        $('.option-list').toggle();
        $('.wrapper .content')[0].style.marginTop="-60px";
    }else {
        //二级页检索结果页：检索框下的词典复选框模块显示
        $('.btn-all').click(function () {
            $(this).toggle();
            $('.option-list').toggle();
            $('.wrapper .content')[0].style.marginTop="-60px";
        });
    }
}

/**
 * @param range 1: 'all'   设置所有table
 *              2:          设置选中table
 */
function setRange(type){
    var tId = '';
    if(type == 'all'){
        $('.option-list li').each(function (index){
            if(index > 0) tId += $(this).attr('urtp-data') + ',';
        })
    }else {
        $('.option-list .selected').each(function (){
            $(this).hasClass('selected')  && !!$(this).attr('urtp-data') ? tId += $(this).attr('urtp-data') + ',' : '';
        })
    }
    $('#tableid').val(tId.substring(0, tId.length - 1));
}

/**
 * @param range 1: ' li'            获取全部表
 *              2:  '.selected'     获取选中表
 */
function getTab(range){
    var s = '';
    $('.option-list' + range).each(function (){
        if(!!$(this).attr('urtp-data')) s += $(this).attr('urtp-data') + ',';
    })
    return s.substring(0, s.length - 1);
}

//推荐词切换为浏览记录
function recommenderFormat(s){
    s = s.split('<!-- &&& -->')[1];
    return s.replace('class=\"dic-result-list\"', 'class=\"list-hook\" data-hook=\"matching\"');
}