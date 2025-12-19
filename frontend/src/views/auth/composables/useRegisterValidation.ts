import { ref, reactive, computed } from 'vue';

export function useRegisterValidation() {
    const username = ref('');
    const password = ref('');
    const confirmPassword = ref('');

    const touched = reactive({
        username: false,
        password: false,
        confirmPassword: false,
    });

    const usernameErrors = computed(() => {
        const errs: any = {};
        if (!username.value) errs.required = true;
        else if (username.value.length < 3) errs.minlength = true;
        return errs;
    });

    const isUserInvalid = computed(() =>
        Object.keys(usernameErrors.value).length > 0
    );

    const passwordErrors = computed(() => {
        const val = password.value;
        const errs: any = {};

        if (!val) {
            errs.required = true;
            return errs;
        }

        if (val.length < 6) errs.minlength = true;
        if (!/[A-Z]/.test(val)) errs.upper = true;
        if (!/[a-z]/.test(val)) errs.lower = true;
        if (!/[0-9]/.test(val)) errs.numeric = true;

        return errs;
    });

    const isPassInvalid = computed(() =>
        Object.keys(passwordErrors.value).length > 0
    );

    const isMismatch = computed(() =>
        password.value !== confirmPassword.value
    );

    const isFormInvalid = computed(() =>
        isUserInvalid.value || isPassInvalid.value || isMismatch.value
    );

    const onBlur = (field: 'username' | 'password' | 'confirmPassword') => {
        touched[field] = true;
    };

    return {
        username,
        password,
        confirmPassword,
        touched,
        usernameErrors,
        passwordErrors,
        isUserInvalid,
        isPassInvalid,
        isMismatch,
        isFormInvalid,
        onBlur,
    };
}
