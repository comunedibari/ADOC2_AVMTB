import { Component } from "../Component";
import { Router } from "../Router";
import { html } from "../render/html";

export class Button extends Component {
    constructor(path, label, id) {
        super();
        this.path = path;
        this.label = label;
        if (id.indexOf('.') > 0 ) {
            this.id = id.substr(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
    }

    async render() {
        return await html`
        <button class="btn btn-secondary" id='${this.id}'>${this.label}</button>`
    }
    
    navigateToPath(event) {
        Router.navigate(this.path);
    }

    after_render() {
        document.querySelector('#' + this.id).addEventListener('click', this.navigateToPath.bind(this));
    }

}