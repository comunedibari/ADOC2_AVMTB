import { html } from "../render/html";
import { Component } from "../Component";
export class Input extends Component {

    constructor(element, classes = "", style = "") {
        super();
        this.element = element;
        this.classes = classes;
        this.style = style;
    }

    render() {
        return html`<div class="${this.classes}" style="${this.style}">  
                    <label for="${this.element.name}" style="padding-right:0.5em;font-size:smaller;">${this.element.value}</label>  
                </div>  `;
    }
}