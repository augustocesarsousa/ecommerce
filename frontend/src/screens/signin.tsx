import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
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

import { useAuthStore } from "../store/authStore";

export function Signin() {
	const { login } = useAuthStore(); // Pega a função de login do Zustand
	const navigate = useNavigate(); // Hook para navegação
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");

	const handleLogin = async () => {
		try {
			await login(email, password);
			navigate("/dashboard"); // Redireciona após login
		} catch (err) {
			setError("Erro ao fazer login. Verifique suas credenciais.");
		}
	};

	return (
		<main className="bg-primary w-full h-screen flex">
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
			<section className="flex items-center justify-center bg-background h-full max-w-3xl w-full p-4">
				<Card className="w-full max-w-md">
					<CardHeader>
						<CardTitle className="text-2xl font-bold tracking-tighter">
							Entre com sua conta
						</CardTitle>
						<CardDescription>Utilize seu email e senha para entrar</CardDescription>
					</CardHeader>
					<CardContent>
						<div>
							<Label htmlFor="email">Email</Label>
							<Input
								id="email"
								placeholder="digite seu email"
								type="email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
							/>
						</div>
						<div className="mt-4">
							<Label htmlFor="password">Senha</Label>
							<Input
								id="password"
								placeholder="digite sua senha"
								type="password"
								value={password}
								onChange={(e) => setPassword(e.target.value)}
							/>
						</div>
						<Button onClick={handleLogin} className="mt-6 w-full">
							Entrar
						</Button>
					</CardContent>
					<CardFooter className="flex flex-col items-center gap-2">
						<p className="text-muted-foreground text-center text-sm">
							Ainda não tem uma conta?{" "}
							<Link to="/signup" className="text-primary font-bold hover:underline">
								Cadastre-se aqui
							</Link>
						</p>
						<p className="text-muted-foreground text-center text-xs">
							Ao entrar na nossa plataforma você concorda com nossos termos de uso e nossa política
							de privacidade.
						</p>
					</CardFooter>
				</Card>
			</section>
		</main>
	);
}
