import { Component } from "../Component";
import { html } from "../render/html";
import './Wait.css';

export class Wait extends Component {
    constructor() {
        super();
    }

    async render(){ 
      return await html`
      <div id="waitPopup" class="modal">
      <div id="loader">
      </div>
      </div>
      `
    }
}