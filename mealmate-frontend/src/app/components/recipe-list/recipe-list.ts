import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {FormsModule} from '@angular/forms'
import { Recipe } from '../../models/recipe.models';
import { RecipeService } from '../../services/recipe.service';

@Component({
  selector: 'app-recipe-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './recipe-list.html',
  styleUrl: './recipe-list.css',
})
export class RecipeList implements OnInit{
  recipes : Recipe[] = [];
  searchQuery = '';
  loading = false

  constructor(private recipeService : RecipeService) {}
    ngOnInit() : void {
      this.loadAll()
    }

    loadAll() : void {
      this.loading = true;
      this.recipeService.getAll().subscribe({
        next: (data) => {this.recipes = data ; this.loading = false},
        error: () => {this.loading = false}
      })
    }

    onSearch() : void {
      if (!this.searchQuery.trim()) {
        this.loadAll()
        return
      }
      this.loading= true
      this.recipeService.search(this.searchQuery).subscribe({
        next : (data) => {this.recipes = data, this.loading = false},
        error: () => {this.loading=false}
      })
    }
}
