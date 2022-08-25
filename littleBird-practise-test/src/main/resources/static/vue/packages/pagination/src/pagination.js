// import Pager from './pager.vue';
//export default Pager;
export default {
    name: "UuiPagination",
    props: {
        total: Number,
        maxTotal: Number,
        hideOnSinglePage: Boolean,
        showTotal: Boolean,
        pageSizes: {
            type: Array,
            default: null
        },
        small: {
            type: Boolean,
            default: false
        },
        id: {
            type: String,
            default: ''
        },
        index: {
            type: Number,
            default: 1
        },
        pageSize: {
            type: Number,
            default: 5
        },
        toggleMode: {
            type: String,
            default: 'chn'
        }
    },
    data() {
        return {
            layout: "prev,pager,next",
            originTotal: "",
            description: '',
            totalCnt: 0,
            curIndex: 1,
            prveText: "",
            nextText: ""
        };
    },
    watch: {
        total(val) {
            this.total = val;
            this.updateTotal();
        },
        index(val) {
            this.index = val;
        }
    },
    methods: {
        handleCurrentChange: function (val) {
            this.$emit("current-change", val, this.id);
            let length = event.target.parentNode.children.length;
            let lastPage = event.target.parentNode.children[length - 1];
            if (this.maxTotal > 0 && (lastPage.attributes['class'].nodeValue.indexOf('active') >= 0)) {
                if (this.layout.indexOf('slot') <= 0) {
                    this.layout = this.layout + ',slot';
                }
                this.description = `已经是末页，产品仅展示前${this.maxTotal}条数据`; //需要考虑语种的问题
            } else if (this.layout.indexOf('slot') > 0) {
                this.layout = this.layout.replaceAll(',slot', '');
            }
        },
        handSizeChange: function (val) {
            this.$emit("size-change", val, this.id);
        },
        updateTotal() {
            if (this.maxTotal > 0) {
                this.originTotal = this.total;
                this.totalCnt = this.maxTotal;
                this.$el.children[0].textContent = '共 ' + this.originTotal + ' 条';
            } else {
                this.totalCnt = this.total;
            }
            if (this.index > 1) {
                this.$children[0].internalCurrentPage = this.index;
            }
        },
        getCustomStyle() {
            $('.el-pagination button').css({'border': '1px solid #E3E3E3', 'padding': '0px 10px', 'margin': '0px 10px'});
            $('.el-pagination__total').css('border', '0px');
            $('.el-pager li').css({'border': '1px solid #E3E3E3', 'margin': '0px 3px'});
        }
    },
    created() {
        if (this.showTotal) {
            this.layout = "total," + this.layout;
        }
        if (!!this.pageSizes && this.pageSizes.length > 0 && this.layout.indexOf("sizes") < 0) {
            this.layout = this.layout + ",sizes";
        }
        if (this.toggleMode === "arrow") {
            this.prveText = "";
            this.nextText = ""
        } else if (this.toggleMode === "chn") {
            this.prveText = "上一页";
            this.nextText = "下一页"
        }
    },
    mounted() {
        this.updateTotal();
    },
    updated() {
        this.getCustomStyle();
    },
    render(h) {
        let inputAttrs = {}
        !!this.pageSizes && this.pageSizes.length > 0 && (inputAttrs["page-sizes"] = this.pageSizes);
        let template = <el-pagination
            {...{attrs: inputAttrs}}
            style="font-weight:normal; margin:10px; text-align:center;"
            hide-on-single-page={this.hideOnSinglePage}
            layout={this.layout}
            total={this.totalCnt}
            on-current-change={this.handleCurrentChange}
            on-size-change={this.handSizeChange}
            small={this.small}
            prev-text={this.prveText}
            next-text={this.nextText}
            page-size={this.pageSize} current-page={this.index}>

            <span class="uui-pagination__description">{this.description}</span>
        </el-pagination>;
        return template;
    }
};
