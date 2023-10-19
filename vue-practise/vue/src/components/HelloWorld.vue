<template>
  <div>
    <drop-menu-navi v-model="query.resourceCode" :drop-menu="dropMenu" :h-menu="hMenu" @resource-switch="resourceSwitch" @cross-resource-range-change="crossResourceRangeChange" />
    <h1>{{ msg }}</h1>
    <h2>Essential Links</h2>
    <ul>
      <li>
        <a
          href="https://vuejs.org"
          target="_blank"
        >
          Core Docs
        </a>
      </li>
      <li>
        <a
          href="https://forum.vuejs.org"
          target="_blank"
        >
          Forum
        </a>
      </li>
      <li>
        <a
          href="https://chat.vuejs.org"
          target="_blank"
        >
          Community Chat
        </a>
      </li>
      <li>
        <a
          href="https://twitter.com/vuejs"
          target="_blank"
        >
          Twitter
        </a>
      </li>
      <br>
      <li>
        <a
          href="http://vuejs-templates.github.io/webpack/"
          target="_blank"
        >
          Docs for This Template
        </a>
      </li>
    </ul>
    <h2>Ecosystem</h2>
    <ul>
      <li>
        <a
          href="http://router.vuejs.org/"
          target="_blank"
        >
          vue-router
        </a>
      </li>
      <li>
        <a
          href="http://vuex.vuejs.org/"
          target="_blank"
        >
          vuex
        </a>
      </li>
      <li>
        <a
          href="http://vue-loader.vuejs.org/"
          target="_blank"
        >
          vue-loader
        </a>
      </li>
      <li>
        <a
          href="https://github.com/vuejs/awesome-vue"
          target="_blank"
        >
          awesome-vue
        </a>
      </li>
    </ul>
  </div>
</template>


<script>
import dropMenuNavi from "./drop_menu_navi";

export default {
  name: 'HelloWorld',
  components: {
    dropMenuNavi
  },
  data () {
    return {
      query: {resourceCode: 'paper'},
      dropMenu: [
        {crossResourceName: '全部', crossResourceCode: 'scdb', default: ['clkj', 'cdmd', 'cipd', 'ccnd', 'clklp', 'clkkp', 'clkhp', 'point', 'clkc'], rightTitle: '全部'},
        {crossResourceName: '论文库', crossResourceCode: 'paper', children: [
            {resourceName: '期刊', resourceCode: 'cjfq', productResourceCode: 'CLKJ', tableId: '537', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '博士', resourceCode: 'cdmd', productResourceCode: 'CLKB', tableId: '539', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '硕士', resourceCode: 'cdmd', productResourceCode: 'CLKM', tableId: '540', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '会议', resourceCode: 'cipd', productResourceCode: 'CLKP', tableId: '542', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '报纸', resourceCode: 'ccnd', productResourceCode: 'CLKN', tableId: '544', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '年鉴', resourceCode: 'cyfd', productResourceCode: 'CLKY', tableId: '4721', crossResourceParent: ['paper', 'scdb']}
          ], default: ['clkj', 'cdmd', 'cipd', 'ccnd']},
        {crossResourceName: '', crossResourceCode: '', children: [
            {resourceName: '工具书', resourceCode: 'crfd', productResourceCode: 'CLKR', tableId: '4726', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '中国专利', resourceCode: 'scod', productResourceCode: 'CLKZ', tableId: '4722', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '海外专利', resourceCode: 'scod', productResourceCode: 'CLKO', tableId: '4723', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '中国标准', resourceCode: 'cisd', productResourceCode: 'CLBH', tableId: '4724', crossResourceParent: ['paper', 'scdb']},
            {resourceName: '海外标准', resourceCode: 'cisd', productResourceCode: 'CLKG', tableId: '4725', crossResourceParent: ['paper', 'scdb']}
          ]},
        {crossResourceName: '法律法规库', crossResourceCode: 'clkl', children: [
            {resourceName: '大陆篇', resourceCode: 'clklp', productResourceCode: 'CLKLP', resourceMark: 'CLKLP', tableId: '609', crossResourceParent: ['clkl', 'scdb']},
            {resourceName: '香港篇', resourceCode: 'clklp', productResourceCode: 'CLKLP', resourceMark: 'CLKKP', tableId: '611', crossResourceParent: ['clkl', 'scdb']},
            {resourceName: '大陆条目', resourceCode: 'clklt', productResourceCode: 'CLKLT', resourceMark: 'CLKLT', tableId: '610'},
            {resourceName: '香港条目', resourceCode: 'clklt', productResourceCode: 'CLKLT', resourceMark: 'CLKKT', tableId: '612'}
          ], default: ['clklp', 'clkkp']},
        {crossResourceName: '', crossResourceCode: '', children: [
            {resourceName: '案例', resourceCode: 'clkc', productResourceCode: 'CLKC', tableId: '608', crossResourceParent: ['scdb']},
            {resourceName: '合同', resourceCode: 'clkhp', productResourceCode: 'CLKHP', tableId: '607', crossResourceParent: ['scdb']},
            {resourceName: '合同条款', resourceCode: 'clkht', productResourceCode: 'CLKHT', tableId: '623'},
            {resourceName: '观点', resourceCode: 'point', productResourceCode: 'CLKVP', tableId: '605', crossResourceParent: ['scdb']}
          ]},
        {crossResourceName: '', crossResourceCode: '', children: [
            {
              productResourceCode: "CLKVC",
              resourceCode: "vedio",
              resourceId: "12",
              resourceName: "法规课程",
              tableId: "604",
              to: {name: 'navi', query: {sysId: this.sysId, resid: '12'}},
              type: "link"
            },
            {
              productResourceCode: "CLKE",
              resourceCode: "xuezhe",
              resourceId: "38",
              resourceName: "专家学者",
              tableId: "4895",
              to: {name: 'navi', query: {sysId: this.sysId, resid: '38'}},
              type: "link"
            }
          ]},
      ],
      hMenu: [
        {resourceCode: 'scdb', title: '全部'},
        {resourceCode: 'cjfq', title: '论文'},
        {resourceCode: 'clkc', title: '案例'},
        {resourceCode: 'clkl', title: '法规'},
        {resourceCode: 'clkhp', title: '合同'},
        {type: 'link', title: '业务助手', to: {name: 'clkccause', query: {sysid: this.sysId}}},
        {type: 'link', title: '一框式检索', to: {name: 'search', query: {sysid: this.sysId}}},
      ],
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    resourceSwitch(tableId){
      console.log(tableId);
    },
    crossResourceRangeChange(tableId){
      console.log(tableId)
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
