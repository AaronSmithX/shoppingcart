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
		Category gadgets = categoryRepo.save(new Category("Gadgets"));
		Category nature = categoryRepo.save(new Category("Nature"));
		Category videoGames = categoryRepo.save(new Category("Video Games"));
		
		// PRODUCTS
			// Gadgets
			Product widget = productRepo.save(new Product("Widget", gadgets, "The original."));
			Product doodad = productRepo.save(new Product("Doodad", gadgets, "Kids love 'em."));
			Product thingamajig = productRepo.save(new Product("Thingamajig", gadgets, "You never know when it will come in handy!"));
			
			// Nature
			Product mountainBike = productRepo.save(new Product("Mountain Bike", nature, "The world's first bicycle made of 100% mountain."));
			Product tent = productRepo.save(new Product("Tent", nature, "Sleeps 4 Americans or 6 Europeans."));
			
			// Video Games
			Product pacman = productRepo.save(new Product("Pac-Man", videoGames, "Take drugs and consume everything, including the undead."));
			Product pokemon = productRepo.save(new Product("Pokémon", videoGames, "Travel the world as a ten year old without adult supervision. Put big monsters in tiny capsules and only let them out for cage fights. Mug grown men and accumulate wealth while enjoying free monster healthcare."));
	}

}
