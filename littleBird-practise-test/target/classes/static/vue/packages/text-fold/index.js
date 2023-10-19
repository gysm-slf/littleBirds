import TextFold from './src/main';

TextFold.install = function(Vue) {
  Vue.component(TextFold.name, TextFold);
};

export default TextFold;
