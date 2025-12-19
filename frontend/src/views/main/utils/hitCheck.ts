export function checkHit(x: number, y: number, r: number): boolean {
    if (!r || r <= 0) return false;

    if (x >= 0 && y >= 0 && x <= r && y <= r) return true;

    if (x <= 0 && x >= -r && y >= 0 && y <= r / 2 && y <= (x + r) / 2) return true;

    if (x <= 0 && y <= 0 && x >= -r && y >= -r && (x * x + y * y <= r * r)) return true;

    return false;
}
