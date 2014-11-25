package templates;

import static util.Globals.*

out << "<!DOCTYPE html>\n"

builder.head {
	title home.class.simpleName
	link rel: 'stylesheet', href: '/css/main.css'
}

builder.body {
	h1 home.class.simpleName
	
	ul {
		li "item 1"
		li "item 2"
	}
}
