$(function () {
    $urtp.Observer.regist('resultsrch-maincontent-loaded-event', function (data) {
        //根据参数中传过来的t、f、v同步检索状态
        var t = $urtp.getUrlCondition('t');
        var f = $urtp.getUrlCondition('f');
        var v = $urtp.getUrlCondition('v');

        if (f.toLowerCase() == "vs2") {
            $(".urtp_search_field").attr("value","vs2");
            $(".urtp_search_field").attr("urtp-data-val","vs2");
            $("[data='Wordmeaning']").addClass("on").siblings().removeClass("on");
            $(".searchmain .btn-wildcard").hide();
        } else {
            $(".urtp_search_field").attr("value","vs1");
            $(".urtp_search_field").attr("urtp-data-val","vs1");
            $("[data='Entryword']").addClass("on").siblings().removeClass("on");
            $(".searchmain .btn-wildcard").show();
        }
        $(".search-menu li").attr("style","color: #f6f6f6;")
        $(".search-menu .on").attr("style","color: #fe0;")

        searchRangeInit(false);

        //这个绑定和resultsrch里的bind中的f和v的绑定有一部分重复
        if (!$urtp.isNull(f) && !$urtp.isNull(v)) {
            f = $urtp.decodeTwice(f);
            $('.urtp_search_item').find('.urtp_search_field').val(f);
            $('.urtp_search_item').find('.urtp_search_field').attr('urtp-data-val', f);
            $('.urtp_search_item').find('.urtp_search_value').val(decodeURIComponent($urtp.filterSpecialCodeForReference($urtp.decodeTwice(v))));
        }
        searchStart = $('.search-input').val().length
        searchEnd = $('.search-input').val().length;

        SearchState(t, data.args.config);
    })
})

//初始化一框检索动态推荐词参数
//flag是否重新加载下拉推荐框
function keyupInit(flag){
    bind();
    $("#tableid").val()=="" && $("#tableid").val($("#index_tableid").val());
    $urtp.Observer.fire('resultsrch-maincontent-loaded-event', {config: config})
    config = $urtp.search.ready(config).config;
    //加载动态推荐框
    flag && new searchClassify();
}