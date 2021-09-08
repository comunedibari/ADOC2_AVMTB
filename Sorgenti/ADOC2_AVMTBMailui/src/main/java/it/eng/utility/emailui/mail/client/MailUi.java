/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package it.eng.utility.emailui.mail.client;

import it.eng.utility.emailui.core.client.callback.ServiceCallback;
import it.eng.utility.emailui.core.client.datasource.GWTRestService;
import it.eng.utility.emailui.core.client.util.JSONUtil;
import it.eng.utility.emailui.mail.shared.bean.OperationDatabaseBean;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MailUi implements EntryPoint {
	public void onModuleLoad() {
		final RootPanel rootPanel = RootPanel.get();
			
		final GWTRestService<OperationDatabaseBean,OperationDatabaseBean> OperationDatabase= new GWTRestService<OperationDatabaseBean,OperationDatabaseBean>("OperationDatabase");
		
		OperationDatabaseBean bean = new OperationDatabaseBean();
		bean.setOperation("STATUS");
		
		OperationDatabase.call(bean, JSONUtil.OperationDatabaseBeanJsonWriter, JSONUtil.OperationDatabaseBeanJsonReader, new ServiceCallback<OperationDatabaseBean>() {
			
			@Override
			public void execute(OperationDatabaseBean result) {
				if(result.getIscreate()){
					rootPanel.add(new Index());
				}else{
					SC.ask("DataBase Create",result.getMessage() , new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value){
								OperationDatabaseBean createoperation = new OperationDatabaseBean();
								createoperation.setOperation("CREATE");
								OperationDatabase.call(createoperation, JSONUtil.OperationDatabaseBeanJsonWriter, JSONUtil.OperationDatabaseBeanJsonReader, new ServiceCallback<OperationDatabaseBean>() {
									public void execute(OperationDatabaseBean object) {
										SC.warn("Tabelle create con successo!");
										rootPanel.add(new Index());
									};
								});
							}
						}
					});
				}
			}
		});
	}
}
