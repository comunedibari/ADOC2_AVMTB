import { Component } from "../Component";
import { html } from "../render/html";

export class Th extends Component {
    constructor(scope = "", classes = "", value = "") {
        super();
        this.scope = scope;
        this.classes = classes;
        this.value = value;
    }
    
    render() {
        return html`<th scope="${this.scope}" class="${this.classes}">${this.value}</th>`;  
    }
}