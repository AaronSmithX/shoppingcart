package org.wecancodeit.shoppingcart;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartPopulator implements CommandLineRunner{

	@Resource
	private ProductRepository productRepo;

	@Resource
	private CategoryRepository categoryRepo;

	@Override
	public void run(String... args) throws Exception {
		// CATEGORIES
		Category gadgets = categoryRepo.save(new Category("Gadgets", "/images/gadgets.png"));
		Category nature = categoryRepo.save(new Category("Nature", "/images/nature.png"));
		Category videoGames = categoryRepo.save(new Category("Video Games", "/images/video-games.png"));
		
		// PRODUCTS
			// Gadgets
			Product widget = productRepo.save(new Product("Widget", gadgets, "The original.", "/images/widget.jpg"));
			Product doodad = productRepo.save(new Product("Doodad", gadgets, "Kids love 'em.", "/images/doodad.jpg"));
			Product thingamajig = productRepo.save(new Product("Thingamajig", gadgets, "You never know when it will come in handy!", "/images/thingamajig.jpg"));
			
			// Nature
			Product mountainBike = productRepo.save(new Product("Mountain Bike", nature, "The world's first bicycle made of 100% mountain.", "/images/mountain-bike.jpg"));
			Product tent = productRepo.save(new Product("Tent", nature, "Sleeps 1 good boy. Treats not included.", "/images/tent.jpg"));
			
			// Video Games
			Product pacman = productRepo.save(new Product("Pac-Man", videoGames, "Take drugs and consume everything, including the undead.", "/images/pac-man.jpg"));
			Product pokemon = productRepo.save(new Product("Pok�mon", videoGames, "Travel the world as a ten year old without adult supervision. Put big monsters in tiny capsules and only let them out for cage fights. Mug grown men and accumulate wealth while enjoying free monster healthcare.", "/images/pokemon.png"));
	}

}
