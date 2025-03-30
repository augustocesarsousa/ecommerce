import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthStore } from "../store/authStore";
import axios from "axios";
import { API_BASE_URL } from "../config";
import { Link } from "react-router-dom";
import {
	Card,
	CardContent,
	CardDescription,
	CardFooter,
	CardHeader,
	CardTitle,
} from "../components/ui/card";
import { Label } from "../components/ui/label";
import { Input } from "../components/ui/input";
import { RadioGroup, RadioGroupItem } from "../components/ui/radio-group"

import {
	Carousel,
	CarouselItem,
	CarouselContent,
	CarouselPrevious,
	CarouselNext,
} from "../components/ui/carousel";
import { Button } from "../components/ui/button";
import img1 from "../assets/1.svg";
import img2 from "../assets/2.svg";
import img3 from "../assets/3.svg";

export function Signup() {
	const navigate = useNavigate();
	const { token, isAuthenticated } = useAuthStore(); // Pega o estado de autenticação
	
	const [userData, setUserData] = useState({
		nome: "",
		login: "",
		email: "",
		password: "",
		telefone: "",
		status: "ativo",
		perfil: "admin",
		
	});
	const [error, setError] = useState("");

	// Atualiza os valores do formulário
	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		setUserData({ ...userData, [e.target.name]: e.target.value });
	};
	const handleSignup = async () => {
		try {
			const response = await axios.post(`${API_BASE_URL}/register/`, userData);

			// Captura o token retornado pelo backend
			const { token } = response.data;

			// Armazena no localStorage
			localStorage.setItem("token", token);

			// Atualiza o estado do Zustand manualmente
			useAuthStore.setState({ token, isAuthenticated: true });

			// Redireciona para a página protegida
			navigate("/dashboard");
		} catch (err) {
			setError("Erro ao registrar usuário. Verifique os dados.");
		}
	};
	return (
		<main className="bg-primary w-full h-screen flex">
			{/* Carrossel */}
			<div className="bg-secondary w-full h-full flex items-center justify-center p-16">
				<Carousel className="w-full max-w-3xl">
					<CarouselContent>
						<CarouselItem>
							<div className="w-full flex justify-center items-center bg-background rounded p-8">
								<img
									src={img1}
									alt="Imagem de executivo"
									className="w-full max-w-lg h-auto object-contain"
								/>
							</div>
						</CarouselItem>
						<CarouselItem>
							<div className="w-full flex justify-center items-center bg-background rounded p-8">
								<img
									src={img2}
									alt="Imagem de executivo"
									className="w-full max-w-lg h-auto object-contain"
								/>
							</div>
						</CarouselItem>
						<CarouselItem>
							<div className="w-full flex justify-center items-center bg-background rounded p-8">
								<img
									src={img3}
									alt="Imagem de executivo"
									className="w-full max-w-lg h-auto object-contain"
								/>
							</div>
						</CarouselItem>
					</CarouselContent>
					<CarouselPrevious />
					<CarouselNext />
				</Carousel>
			</div>

			{/* Formulário de Cadastro */}
			<section className="flex items-center justify-center bg-background h-full max-w-3xl w-full p-4">
				<Card className="w-full max-w-md">
					<CardHeader>
						<CardTitle className="text-2xl font-bold tracking-tighter">Crie sua conta</CardTitle>
						{error && <p className="text-red-500">{error}</p>}
						<CardDescription>Preencha os campos abaixo para criar sua conta</CardDescription>
					</CardHeader>
					<CardContent>
						<div>
							<Label htmlFor="name">Nome</Label>
							<Input
								id="name"
								placeholder="Digite seu nome"
								type="text"
								name="nome"
								value={userData.nome}
								onChange={handleChange}
							/>
						</div>

						<div>
							<Label htmlFor="name">login</Label>
							<Input
								id="name"
								placeholder="Digite seu login"
								type="text"
								name="login"
								value={userData.login}
								onChange={handleChange}
							/>
						</div>

						<div className="mt-4">
							<Label htmlFor="password">Senha</Label>
							<Input
								id="password"
								placeholder="Crie uma senha"
								type="password"
								name="password"
								value={userData.password}
								onChange={handleChange}
							/>
						</div>

						<div className="mt-4">
							<Label htmlFor="telefone">telefone</Label>
							<Input
								id="telefone"
								placeholder="digite um telefone"
								type="number"
								name="telefone"
								value={userData.telefone}
								onChange={handleChange}
							/>
						</div>
						
						<div className="mt-4">
							<Label htmlFor="email">Email</Label>
							<Input
								id="email"
								placeholder="Digite seu email"
								type="email"
								name="email"
								value={userData.email}
								onChange={handleChange}
							/>
						</div>

						<div className="mt-4">
							<Label className="mb-2 block">Status</Label>
							<RadioGroup
								name="status"
								value={userData.status}
								onValueChange={(value) => setUserData({ ...userData, status: value })}
								className="flex gap-4"
							>
								<div className="flex items-center space-x-2">
									<RadioGroupItem value="ativo" id="status-ativo" />
									<Label htmlFor="status-ativo">Ativo</Label>
								</div>
								<div className="flex items-center space-x-2">
									<RadioGroupItem value="inativo" id="status-inativo" />
									<Label htmlFor="status-inativo">Inativo</Label>
								</div>
							</RadioGroup>
						</div>

						<div className="mt-4">
							<Label className="mb-2 block">Perfil</Label>
							<RadioGroup
								name="perfil"
								value={userData.perfil}
								onValueChange={(value) => setUserData({ ...userData, perfil: value })}
								className="flex flex-col gap-2"
							>
								<div className="flex items-center space-x-2">
									<RadioGroupItem value="admin" id="perfil-admin" />
									<Label htmlFor="perfil-admin">Admin</Label>
								</div>
								<div className="flex items-center space-x-2">
									<RadioGroupItem value="comprador" id="perfil-comprador" />
									<Label htmlFor="perfil-comprador">Comprador</Label>
								</div>
								<div className="flex items-center space-x-2">
									<RadioGroupItem value="operador" id="perfil-operador" />
									<Label htmlFor="perfil-operador">Operador</Label>
								</div>
							</RadioGroup>
						</div>
						<Button className="mt-6 w-full" onClick={handleSignup}>
							Cadastrar
						</Button>
					</CardContent>
					<CardFooter className="flex flex-col items-center gap-2">
						<p className="text-muted-foreground text-center text-sm">
							Já tem uma conta?{" "}
							<Link to="/" className="text-primary font-bold hover:underline">
								Faça login
							</Link>
						</p>
					</CardFooter>
				</Card>
			</section>
		</main>
	);
}
