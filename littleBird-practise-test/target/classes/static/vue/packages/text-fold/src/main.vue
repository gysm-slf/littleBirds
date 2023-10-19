<template>
  <!-- 只显示摘要的画面 -->
  <div v-if="isHide" class="hideBg">
    <p ref="content" v-html="content.replaceAll('\r\n', '<br>')" class="summary" :style="hideStyle"></p>
    <div class="showBtn" v-show="isNeedExpand">
      <a href="#" @click.stop.prevent="onShow">展开阅读全文&nbsp;
        <span class="downArrow"></span>
      </a>
    </div>
  </div>
  <!-- 显示完整内容的画面 -->
  <div v-else class="showBg">
    <p v-html="content.replaceAll('\r\n', '<br>')"></p>
    <div class="hideBtn" v-show="isNeedExpand">
      <a href="#" @click.stop.prevent="onHide">收起&nbsp;
        <span class="upArrow"></span>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: "text-fold",
  props: {
    content: {
      type: String,
      default: '中华人民共和国位于亚洲东部，太平洋西岸 [1]  ，是工人阶级领导的、以工农联盟为基础的人民民主专政的社会主义国家 [2]  。1949年（己丑年）10月1日成立 [3-4]  ，以五星红旗为国旗 [5]  ，《义勇军进行曲》为国歌 [6]  ，国徽内容包括国旗、天安门、齿轮和麦稻穗 [7]  ，首都北京 [8]  ，省级行政区划为23个省、5个自治区、4个直辖市、2个特别行政区 [9]  ，是一个以汉族为主体民族，由56个民族构成的统一多民族国家，汉族占总人口的91.51% [10]  。新中国成立后，随即开展经济恢复与建设 [11]  ，1953年开始三大改造 [12]  ，到1956年确立了社会主义制度，进入社会主义探索阶段 [13]  。文化大革命之后开始改革开放，逐步确立了中国特色社会主义制度。 [14] 中华人民共和国陆地面积约960万平方公里，大陆海岸线1.8万多千米，岛屿岸线1.4万多千米，内海和边海的水域面积约470多万平方千米。海域分布有大小岛屿7600多个，其中台湾岛最大，面积35798平方千米。 [1]  陆地同14国接壤，与6国海上相邻。'
    },
    lineNumber: {
      type: Number,
      //n行以上后开启折叠
      default: 10
    }
  },
  data(){
    return {
      isHide: true,        //true 显示部分内容  false 显示完整内容
      isNeedExpand: true,  //是否需要开启折叠/展开功能
      hideStyle: 'overflow: hidden; text-overflow: clip; display: -webkit-box; -webkit-box-orient: vertical; font-size: 16px;'
    }
  },
  mounted() {
    this.hideStyle += ' -webkit-line-clamp: ' + this.lineNumber.toString() + ';';

    let abstractHeight = window.getComputedStyle(this.$refs.content).height.replace('px', '');
    if (parseInt(abstractHeight) < 30 * this.lineNumber) {
      //无需折叠
      this.isHide = false;
      this.isNeedExpand = false;
    } else {
      //折叠
      this.isHide = true;
    }
  },
  methods: {
    onShow: function(){
      this.isHide = false;    //点击onShow切换为false，显示为展开画面
    },
    onHide: function(){
      this.isHide = true;    //点击onHide切换为true，显示为折叠画面
    }
  }
}
</script>

<style scoped>
/* 摘要背景板 */
.hideBg {
  width: 100%;
  background-color: #e9ecef;
  /*margin: 1.5rem;*/
  /*padding: 1.5rem;*/
  padding-bottom: 0;    /* 方便渐变层遮挡 */
  position: relative;    /* 用于子元素定位 */
}
/* 全文背景板，基本与摘要相同 */
.showBg {
  width: 100%;
  background-color: #e9ecef;
  /*margin: 1.5rem;*/
  /*padding: 1.5rem;*/
}
/* 摘要内容 */
/*.summary {*/
/*  overflow: hidden;    !* 隐藏溢出内容 *!*/
/*  text-overflow: clip;    !* 修剪文本 *!*/
/*  display: -webkit-box;    !* 弹性布局 *!*/
/*  -webkit-box-orient: vertical;    !* 从上向下垂直排列子元素 *!*/
/*  -webkit-line-clamp: 10;    !* 限制文本仅显示前n行 *!*/
/*  font-size: 16px;*/
/*}*/
#example p {
  text-indent: 2em;
}
/* 展开按钮 */
.showBtn {
  width: 100%;    /* 与背景宽度一致 */
  height: 3rem;
  /*position: absolute;    !* 相对父元素定位 *!*/
  top: 3rem;    /* 刚好遮挡在最后两行 */
  left: 0;
  z-index: 0;    /* 正序堆叠，覆盖在p元素上方 */
  text-align: center;
  /*background: linear-gradient(rgba(233,236,239,.5), white);    !* 背景色半透明到白色的渐变层 *!*/
  /*padding-top: 3rem;*/
}
/* 收起按钮 */
.hideBtn {
  text-align: right;
}
.showBg p {
  font-size: 16px;
}
/* 向下角箭头 */
.downArrow {
  display: inline-block;
  width: 8px;    /* 尺寸不超过字号的一半为宜 */
  height: 8px;
  border-right: 1px solid;    /* 画两条相邻边框 */
  border-bottom: 1px solid;
  transform: rotate(45deg);    /* 顺时针旋转45° */
  margin-bottom: 3px;
}
/* 向上角箭头，原理与下箭头相同 */
.upArrow {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-left: 1px solid;
  border-top: 1px solid;
  transform: rotate(45deg);
  margin-top: 3px;
}
</style>
