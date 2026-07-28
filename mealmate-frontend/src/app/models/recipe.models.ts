export interface RecipeIngredient {
  ingredientId: number;
  ingredientName: string;
  quantity: number;
  uniy: string
}

export interface Recipe {
  id : number;
  title : string ;
  description: string;
  instructions:string;
  preparationTime: number;
  cookTime : number;
  difficulty : string;
  baseServings : number;
  tagRegimes: string[];
  caloriePerServing : number;
  imageUrl: string;
  recipeType : string;
  ingredients : RecipeIngredient[]

}
