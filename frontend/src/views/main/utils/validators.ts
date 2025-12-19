export interface ValidateResult {
    ok: boolean;
    error?: string;
}

export function validateXYR(x: string, y: string, r: string): ValidateResult {
    const xStr = x.replace(',', '.');
    if (xStr.trim() === '' || isNaN(Number(xStr))) {
        return { ok: false, error: 'X не число' };
    }
    const xNum = parseFloat(xStr);
    if (xNum < -3 || xNum > 5) {
        return { ok: false, error: 'X от -3 до 5' };
    }

    const yStr = y.replace(',', '.');
    if (yStr.trim() === '' || isNaN(Number(yStr))) {
        return { ok: false, error: 'Y не число' };
    }
    const yNum = parseFloat(yStr);
    if (yNum < -5 || yNum > 5) {
        return { ok: false, error: 'Y от -5 до 5' };
    }

    const rStr = r.replace(',', '.');
    if (rStr.trim() === '' || isNaN(Number(rStr))) {
        return { ok: false, error: 'R не число' };
    }
    const rNum = parseFloat(rStr);
    if (rNum <= 0 || rNum > 5) {
        return { ok: false, error: 'R должен быть в диапазоне (0, 5]' };
    }

    return { ok: true };
}
