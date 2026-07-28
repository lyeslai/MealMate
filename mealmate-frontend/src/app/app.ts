import { Component, signal } from '@angular/core';
import { RecipeList } from './components/recipe-list/recipe-list';

@Component({
  selector: 'app-root',
  imports: [RecipeList],
  template: '<app-recipe-list></app-recipe-list>',
  styleUrl: './app.css'
})
export class App {

}
