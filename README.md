# BakingApp

## Overview
This Android app does look recipes from this ["API", .json file](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json).

## Purpose
This project is for Android Developer Nanodegree program I'm participating. It is not trying to be nicest looking app, as it was done just for the learning purposes.

## Libraries
* [ExoPlayer](https://github.com/google/ExoPlayer) to play video files
* [Retrofit](http://square.github.io/retrofit/) +[Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) to make API requests and converting response to objects
* [Butter Knife](http://jakewharton.github.io/butterknife/) injecting fields and get rid of multiple **findViewById**
* [Espresso](https://developer.android.com/training/testing/espresso/) to test UI
* [Picasso](http://square.github.io/picasso/) to download images

## Links
* [TinyDB](https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo) - Got the idea of storing the ingredients to SharedPreferences for the widget
* [CountingIdlingResource]((https://medium.com/@wingoku/synchronizing-espresso-with-custom-threads-using-idling-resource-retrofit-70439ad2f07) - Looked this tutorial to learn how to make Retrofit working with espresso's UI test
* [RoboPOJOGenerator](https://github.com/robohorse/RoboPOJOGenerator) - Used this tool to generate the start of POJOS

## Using the widget
1. Add the widget to your phone's desktop
2. Click the the "No recipe selected" to open the app
3. Now choose the recipe
4. After choosing of recipe, app will minimize and you should see the widget to have it's content updated with your selected recipe.

## Screenshots
## Phone
<img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/recipe_selection.png?raw=true
" width="200" alt="Recipe selection"> <img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/all_steps.png?raw=true
"width="200" alt="All steps"> <img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/ingredients.png?raw=true
"width="200" alt="Ingredients"> <img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/recipe_step.png?raw=true
"width="200" alt="Step">

## Tablet
<img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/tablet_recipe_selection.png?raw=true
" width="400" alt="Recipe selection"> <img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/tablet_recipe_step.png?raw=true
"width="400" alt="Step">

### Widget
<img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/widget_no_recipe_selected.png?raw=true
" width="200" alt="No recipe selected"> <img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/widget_choose_recipe_for_widget.png?raw=true
"width="200" alt="Choose recipe for widget"> <img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/widget_recipe_stored.png?raw=true
"width="200" alt="Recipe stored"> <img src="
https://github.com/skipadu/BakingApp/raw/master/screenshots/widget_recipe_selected.png?raw=true
"width="200" alt="Recipe selected"> 
