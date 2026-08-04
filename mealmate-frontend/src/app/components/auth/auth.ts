import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.html'
})
export class AuthComponent {
  mode: 'login' | 'register' = 'register';

  email = '';
  password = '';
  pseudo = '';

  errorMessage = '';
  successMessage = '';
  loggedInPseudo: string | null = null;

  constructor(private authService: AuthService) {
    this.loggedInPseudo = this.authService.getPseudo();
  }

  switchMode(mode: 'login' | 'register'): void {
    this.mode = mode;
    this.errorMessage = '';
    this.successMessage = '';
  }

  submit(): void {
    this.errorMessage = '';
    this.successMessage = '';

    const request = this.mode === 'register'
      ? this.authService.register({ email: this.email, password: this.password, pseudo: this.pseudo })
      : this.authService.login({ email: this.email, password: this.password });

    request.subscribe({
      next: (res) => {
        this.successMessage = `Connecté en tant que ${res.pseudo}`;
        this.loggedInPseudo = res.pseudo;
      },
      error: (err) => {
        this.errorMessage = err.error?.error || 'Une erreur est survenue';
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.loggedInPseudo = null;
    this.successMessage = '';
  }
}
