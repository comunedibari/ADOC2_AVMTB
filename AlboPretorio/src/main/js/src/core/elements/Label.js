import { html } from "../render/html";
import { Component } from "../Component";
export class Label extends Component {

    constructor(name, value, classes = "", style = "") {
        super();
        this.name = name;
        this.value = value;
        this.classes = classes;
        this.style = style;
    }

    render() {
        return html`<div class="${this.classes}" style="${this.style}">  
                    <label for="${this.name}" style="padding-right:0.5em;font-size:smaller;">${this.value}</label>  
                </div>  `;
    }
}