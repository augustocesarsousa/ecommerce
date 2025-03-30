import { create } from "zustand";
import axios from "axios";
import { API_BASE_URL } from "../config";

// Definição do tipo para o estado da autenticação
interface AuthState {
	token: string | null;
	isAuthenticated: boolean;
	login: (email: string, password: string) => Promise<void>;
	logout: () => void;
}

// Criar o store de autenticação com Zustand
export const useAuthStore = create<AuthState>((set) => ({
	token: localStorage.getItem("token") || null,
	isAuthenticated: !!localStorage.getItem("token"),

	login: async (email, password) => {
		try {
			const response = await axios.post(`${API_BASE_URL}/login/`, {
				email,
				password,
			});

			const { token } = response.data;

			// Armazena o token no localStorage
			localStorage.setItem("token", token);

			// Atualiza o estado do Zustand
			set({ token, isAuthenticated: true });
		} catch (error) {
			console.error("Erro ao fazer login:", error);
			throw error;
		}
	},

	logout: () => {
		// Remove o token do localStorage
		localStorage.removeItem("token");

		// Atualiza o estado do Zustand
		set({ token: null, isAuthenticated: false });
	},
}));
