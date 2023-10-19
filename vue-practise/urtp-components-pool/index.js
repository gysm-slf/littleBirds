import DropMenuNavi from './packages/drop-menu-navi/index';

const components = [
    DropMenuNavi
];

const install = function (Vue, opts = {}) {
    components.forEach(component => {
        Vue.component(component.name, component);
    });
}

export default {
    version: '1.0.0',
    DropMenuNavi,
    install
}