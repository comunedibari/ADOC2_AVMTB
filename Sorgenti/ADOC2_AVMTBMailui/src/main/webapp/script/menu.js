var CLIPBOARD = "";

$(function(){
$(document).contextmenu({
		delegate: ".hasmenu",
		preventContextMenuForPopup: true,
		preventSelect: true,
		taphold: true,
		menu: [
			
			],
		// Handle menu selection to implement a fake-clipboard
		select: function(event, ui) {
			var $target = ui.target;
			switch(ui.cmd){
			case "copy":
				CLIPBOARD = $target.text();
				break
			case "paste":
				CLIPBOARD = "";
				break
			}
			alert("select " + ui.cmd + " on " + $target.prop("id"));
			// Optionally return false, to prevent closing the menu now
		},
		// Implement the beforeOpen callback to dynamically change the entries
		beforeOpen: function(event, ui) {
			var scope = myScope;
			var $menu = ui.menu,
			$target = ui.target;
			targetToOpen = $target;

			ui.menu.zIndex( $(event.target).zIndex() + 1);
			if (showMenu) {
				showMenu = false;
				if (menuItems!=undefined && menuItems.length == 0) {
					return false;
				} else return true;
			}
			else {
				
				var idRecord = targetToOpen.prop("id");
				servizio.getMenuWithCallback( scope, idRecord, function(data){
				menuItems = data;
				showMenu = true;
				for (i = 0; i<data.length; i++){
					$(document).contextmenu("setEntry", data[i].cmd, data[i].title);
					$(document).contextmenu("enableEntry", data[i].cmd, data[i].enabled);
				}
				
				$(document).contextmenu("open", targetToOpen, {foo: "bar"});
				} );
				return false;
			}
				
		}
	});
});

function showMenuClick(element, idElement){
	var scope = myScope;
	var idRecord = idElement.substring("menuCliccabile_".length);
	servizio.getMenuWithCallback( scope, idRecord, function(data){
				menuItems = data;
				showMenu = true;
				for (i = 0; i<data.length; i++){
					$(document).contextmenu("setEntry", data[i].cmd, data[i].title, data[i].enabled);
					$(document).contextmenu("enableEntry", data[i].cmd, data[i].enabled);
				}
				var selector = "#" + idElement;
				var target = $(selector);
				$(document).contextmenu("open", target, {foo: "bar"});
	});
}	


var showMenu = false;
var targetToOpen = null;
var menu;
var menuItems;