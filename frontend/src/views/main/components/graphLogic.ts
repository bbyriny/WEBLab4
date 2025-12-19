import { computed } from 'vue';
import type { PointResponse } from '../../../services/points.service.ts';
import { checkHit } from '../utils/hitCheck.ts';

export const useGraphScript = (props: {
    points: PointResponse[];
    currentR: number;
}, emit: (e: 'graph-click', x: number, y: number) => void) => {

    const GRAPH_WIDTH = 350;
    const GRAPH_HEIGHT = 350;
    const CENTER_X = GRAPH_WIDTH / 2;
    const CENTER_Y = GRAPH_HEIGHT / 2;
    const SCALE = 30;

    const rPx = computed(() => props.currentR * SCALE);
    const halfRPx = computed(() => (props.currentR / 2) * SCALE);

    const drawPoints = computed(() => {
        return props.points.map(p => ({
            ...p,
            cx: CENTER_X + p.x * SCALE,
            cy: CENTER_Y - p.y * SCALE,
            isHitNow: props.currentR > 0 ? checkHit(p.x, p.y, props.currentR) : p.isHit
        }));
    });

    const onSvgClick = (event: MouseEvent) => {
        const svg = event.currentTarget as SVGSVGElement;
        const rect = svg.getBoundingClientRect();
        const clickX = event.clientX - rect.left;
        const clickY = event.clientY - rect.top;

        let x = (clickX - CENTER_X) / SCALE;
        let y = (CENTER_Y - clickY) / SCALE;

        x = parseFloat(x.toFixed(2));
        y = parseFloat(y.toFixed(2));

        emit('graph-click', x, y);
    };

    return {
        GRAPH_WIDTH,
        GRAPH_HEIGHT,
        CENTER_X,
        CENTER_Y,
        rPx,
        halfRPx,
        drawPoints,
        onSvgClick
    };
};
