import api from '../api/axios';

export interface PointRequest {
  x: number;
  y: number;
  r: number;
}

export interface PointResponse {
  id: number;
  x: number;
  y: number;
  r: number;
  isHit: boolean;
  startTime: string;
  executionTime: number;
  creator: string;
}

export const pointsService = {
  async addPoint(point: PointRequest) {
    
    const res = await api.post<PointResponse>('/points', point);
    return res.data;
  },

  async getPoints() {
    const res = await api.get<PointResponse[]>('/points');
    return res.data;
  },

  async deleteAllPoints() {
    await api.delete('/points');
  }
};