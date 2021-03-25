import { Component } from "../Component";
import { html } from "../render/html";

export class IconButton extends Component {
    
    constructor(title, id, onClickFun, icon) {
        super();
        this.title = title;
        if (id.indexOf('.') > 0 ) {
            this.id = id.substr(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
        this.icon = icon;
        this.onClickFun = onClickFun;
    }

    async render() {
        return await html`<a><i id="${this.id}" title="${this.title}" class="fa fa-${this.icon} text-secondary" style="font-size: x-large;"></i></a>&nbsp;`
    }

    after_render() {
        document.querySelector('#' + this.id).addEventListener('click', this.onClickFun);
    }

}