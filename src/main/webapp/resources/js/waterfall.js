function waterfall(container, selector, gutter){
	$(container).imagesLoaded(function(){
		$(container).masonry({
			itemSelector: selector,
			gutter: gutter
		});
	});
}