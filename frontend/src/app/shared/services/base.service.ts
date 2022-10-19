import { HttpClient } from '@angular/common/http';
import { SelectItem } from 'primeng';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PageChangeEvent } from '../models/events/page-change.event';
import { Page } from '../models/page.model';
import { RequestUtil } from '../utils/request.util';

export abstract class BaseService {
	baseUrl: string;
	http: HttpClient;
	entity: string;

	protected constructor(http: HttpClient, entity: string, servicoUrl: string = environment.serviceUrl) {
		this.http = http;
		this.entity = entity;
		this.baseUrl = `${ servicoUrl }${ environment.apiUrl }`;
	}

	public getUrl(): string {
		return `${ this.baseUrl }/${ this.entity }`;
	}

	public getAsSelectItem(): Observable<SelectItem[]> {
		return this.http.get<SelectItem[]>(`${ this.getUrl() }/dropdown`);
	}

	public getUrlId(id: number | string): string {
		return `${ this.getUrl() }/${ id }`;
	}

	public findAll<T>(event: LazyLoadEvent): Observable<Page<T>> {
		const params = RequestUtil.getParamsFromLazyLoadEvent(event);
		return this.http.get<Page<T>>(this.getUrl(), { params: params });
	}

	public findAllPageChange<T>(event: PageChangeEvent): Observable<Page<T>> {
		const params = RequestUtil.getParamsFromPageChangeEvent(event);
		return this.http.get<Page<T>>(this.getUrl(), { params: params });
	}

	public filter<T>(filter: object, event?: LazyLoadEvent): Observable<Page<T>> {
		const params = RequestUtil.getParamsFromLazyLoadEvent(event);
		return this.http.post<Page<T>>(`${ this.getUrl() }/filtro`, filter, { params });
	}

	public findById<T>(id: number | string): Observable<T> {
		return this.http.get<T>(this.getUrlId(id));
	}

	public insert<T>(data: object): Observable<T> {
		return this.http.post<T>(this.getUrl(), data);
	}

	public update<T>(data: object, id: number | string): Observable<T> {
		return this.http.put<T>(this.getUrlId(id), data);
	}

	public delete(id: number | string): Observable<void> {
		return this.http.delete<void>(this.getUrlId(id));
	}
}
