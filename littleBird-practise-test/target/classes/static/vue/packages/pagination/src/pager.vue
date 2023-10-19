<!--
<template>
  <div style="position:relative;">
    <el-pagination
      background
      :hide-on-single-page="hideOnSinglePage"
      :layout="layout"
      :total="totalCnt"
      :page-sizes="pageSizes"
      @current-change="handleCurrentChange"
      @size-change="handSizeChange"
      style="display:inline-block"
      :small="small"
    >
    <span class="uui-pagination__description" v-show="showDescription">{{description}}</span>
    </el-pagination>
  </div>
</template>
<script>
export default {
  name: "UuiPagination",
  props: {
    total: Number,
    maxTotal: Number,
    hideOnSinglePage: Boolean,
    showTotal: Boolean,
    pageSizes: {
      type:Array,
      default:null
    },
    small: {
      type:Boolean,
      default:false
    },
    id:{
        type:String,
        default:''
    },
    index:
    {
        type:Number,
        default:1
    },
  },
  data() {
    return {
      layout: "prev,pager,next,slot",
      originTotal: "",
      description:'',
      showDescription:false,
      totalCnt:0
    };
  },
  watch:{
    total(val){
      this.total=val;
      this.updateTotal();
    }
  },
  methods: {
    handleCurrentChange: function (val) {
      this.$emit("current-change", val,this.id);
      let length=event.target.parentNode.children.length;
      let lastPage=event.target.parentNode.children[length-1];
      if(this.maxTotal>0&&(lastPage.attributes['class'].nodeValue.indexOf('active')>=0))
      {
          this.showDescription=true;
          this.description=`已经是末页，产品仅展示前${this.maxTotal}条数据`; //需要考虑语种的问题
      }
      else
      {
          this.showDescription=false;
      }
    },
    handSizeChange: function (val) {
      this.$emit("size-change", val,this.id);
    },
    updateTotal()
    {
      if (this.maxTotal > 0) {
        this.originTotal = this.total;
        this.totalCnt=this.maxTotal;
        this.$el.children[0].children[0].textContent ='共 ' + this.originTotal + ' 条'; 
      }
      else
      {
          this.totalCnt=this.total;
      }
      if(this.index>1)
      {
          this.$children[0].internalCurrentPage= this.index;
      }
    }
  },
  created() {
    if (this.showTotal) {
      this.layout = "total," + this.layout;
    }
    if (this.pageSizes.length > 0 && this.layout.indexOf("sizes") < 0) {
      this.layout = this.layout + ",sizes";
    }
  },
  mounted() {
    this.updateTotal()
  },
};
</script>
-->