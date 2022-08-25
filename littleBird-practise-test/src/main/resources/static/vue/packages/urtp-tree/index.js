import UrtpTree from './src/main';

UrtpTree.install = function(Vue) {
  Vue.component(UrtpTree.name, UrtpTree);
};

export default UrtpTree;
