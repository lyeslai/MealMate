import { Recipe } from './../models/recipe.models';
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class RecipeService {
  private baseUrl = 'http://localhost:8080/api/recipes'

  constructor(private http: HttpClient) {}

  getAll(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.baseUrl);
  }

  search(query : string) : Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}?search=${encodeURIComponent(query)}`)
  }

}
