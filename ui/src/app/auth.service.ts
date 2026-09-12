import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { catchError, of, tap } from 'rxjs';

export interface AuthUser {
  authenticated: boolean;
  name: string | null;
  email: string | null;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);

  readonly user = signal<AuthUser>({ authenticated: false, name: null, email: null });

  loadUser(): void {
    this.http.get<AuthUser>('/auth/me').pipe(
      catchError(() => of({ authenticated: false, name: null, email: null })),
    ).subscribe((user) => this.user.set(user));
  }

  login(): void {
    window.location.assign('/oauth2/authorization/google');
  }

  logout(): void {
    this.http.post('/logout', {}, { responseType: 'text' }).pipe(
      tap(() => this.user.set({ authenticated: false, name: null, email: null })),
    ).subscribe();
  }
}