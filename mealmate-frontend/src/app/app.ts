import { Component, signal } from '@angular/core';
import { RecipeList } from './components/recipe-list/recipe-list';
import { AuthComponent } from './components/auth/auth';
@Component({
  selector: 'app-root',
  imports: [RecipeList, AuthComponent],
  template: `
      <app-auth></app-auth>
  <app-recipe-list></app-recipe-list>`,
  styleUrl: './app.css'
})
export class App {

}
