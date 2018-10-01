#include <stdio.h>
#include <pwd.h>

int main()
{
	struct passwd *p = getpwnam("lh150094");
	struct passwd pswd = *p;

	printf("%s\n", pswd.pw_shell);
	return 0;
}