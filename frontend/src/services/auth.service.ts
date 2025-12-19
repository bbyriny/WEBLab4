import api from '../api/axios';
import { jwtDecode } from 'jwt-decode';

export interface AuthCredentials {
  username: string;
  password: string;
}

interface TokenResponse {
  token: string;
}

interface CustomJwtPayload {
  sub: string;
  exp: number;
}

export const authService = {
  async login(creds: AuthCredentials) {
    const res = await api.post<TokenResponse>('auth/login', creds);
    if (res.data.token) {
      localStorage.setItem('authToken', res.data.token);
    }
    return res.data;
  },

  async register(creds: AuthCredentials) {
    return api.post('/auth/register', creds);
  },

  logout() {
    localStorage.removeItem('authToken');
  },

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  },

  getUsername(): string {
    const token = localStorage.getItem('authToken');
    if (!token) return 'Гость';
    try {
      const decoded = jwtDecode<CustomJwtPayload>(token);
      return decoded.sub;
    } catch (e) {
      return 'Гость';
    }
  }
};