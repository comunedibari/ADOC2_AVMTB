import { Component, html } from "../../core";
import { PageTitle } from "../components/page";

class PageNotFound extends Component {

render() {
    return html`
        <section class="section">
          <div class="row">
            ${new PageTitle('Pagina non trovata')}
          </div>
        </section>
      `;
  }
}



  // export dell'unica instanza della pagina
  export default new PageNotFound();