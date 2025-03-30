import React from "react";
import { Routes, Route } from "react-router-dom";
import { Signin } from "./screens/signin";
import { Signup } from "./screens/signup";
import { Dashboard } from "./screens/dashboard";

export function App() {
	return (
		<Routes>
			<Route path="/" element={<Signin />} />
			<Route path="/signup" element={<Signup />} />
			<Route path="/dashboard" element={<Dashboard />} />
		</Routes>
	);
}
