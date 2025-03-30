module.exports = {
	// Define este arquivo como a configuração raiz do ESLint, impedindo que ele herde configurações de diretórios acima
	root: true,
	env: {
		// Define que o ambiente é de navegador (permite uso de objetos como window, document, etc)
		browser: true,
		// Define que o código usa funcionalidades modernas do ECMAScript 2021+
		es2021: true,
	},
	extends: [
		"eslint:recommended", // Ativa um conjunto básico de regras recomendadas pelo ESLint
		"plugin:react/recommended", // Ativa regras recomendadas para projetos React
		"plugin:react-hooks/recommended", // Ativa regras específicas para uso correto de React Hooks
		"plugin:@typescript-eslint/recommended", // Ativa regras recomendadas para projetos com TypeScript
		"prettier", // Desativa regras do ESLint que conflitam com o Prettier
	],
	// Define o parser que irá entender a sintaxe do TypeScript
	parser: "@typescript-eslint/parser",
	parserOptions: {
		// Define a versão do ECMAScript usada no projeto (a mais recente disponível)
		ecmaVersion: "latest",
		// Indica que o código está no formato de módulo (permite uso de import/export)
		sourceType: "module",
	},
	plugins: [
		"react", // Plugin para aplicar regras específicas do React
		"@typescript-eslint", // Plugin para aplicar regras específicas do TypeScript
		"prettier", // Plugin para integrar o Prettier ao ESLint
	],
	rules: {
		// Exibe um aviso quando o código não estiver formatado conforme o Prettier
		"prettier/prettier": "warn",
		// Desativa a exigência de importar o React no escopo ao usar JSX (não necessário no Next.js ou Vite)
		"react/react-in-jsx-scope": "off",
		// Emite avisos sobre variáveis não utilizadas, mas ignora aquelas que começam com "_"
		"@typescript-eslint/no-unused-vars": [
			"warn",
			{ argsIgnorePattern: "^_", varsIgnorePattern: "^_" },
		],
		// Emite avisos sempre que um console.log (ou outro console) for usado no código
		"no-console": "warn",
		// Desativa a exigência de definir tipos com PropTypes, pois o TypeScript já cuida disso
		"react/prop-types": "off",
	},
	settings: {
		react: {
			// Faz com que o plugin do React detecte automaticamente a versão usada no projeto
			version: "detect",
		},
	},
};
