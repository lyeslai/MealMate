import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AuthResponse, LoginRequest, RegisterRequest } from "../models/auth.model";
import { tap } from "rxjs/internal/operators/tap";
import { pipe } from "rxjs/internal/util/pipe";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/auth';
  constructor(private http: HttpClient) {}

  register(data : RegisterRequest) {
    return this.http.post<AuthResponse>(`${this.baseUrl}/register`, data)
    .pipe(tap((res)=> this.storeToken(res.token)));
  }

  login(data: LoginRequest) {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, data)
    .pipe(tap((res)=> this.storeToken(res.token)));
  }

  private storeToken(token: string) {
    localStorage.setItem('token', token);
    localStorage.setItem('pseudo', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getPseudo(): string | null {
    return localStorage.getItem('pseudo');
  }

  logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('pseudo')
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
