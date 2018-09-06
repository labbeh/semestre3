int somme(int a, int b)
{
	int result = a+b;
	return result;
}

int diff(int a, int b)
{
	int result = a-b;
	return result;
}

int mult(int a, int b)
{
	int result = a*b;
	return result;
}

int div(int a, int b)
{
	if(b == 0) return 0;
	
	int result = a/b;
	return result;
}
