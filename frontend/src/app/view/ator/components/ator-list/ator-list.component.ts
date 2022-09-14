import { Component, ViewChild } from '@angular/core';
import { PageNotificationService } from '@nuvem/primeng-components';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { LazyLoadEvent } from 'primeng/api';
import { Table } from 'primeng/table';
import { finalize } from 'rxjs/operators';
import { PageListEnum } from '../../../../shared/enums/page-list.enum';
import { Page } from '../../../../shared/models/page.model';
import { MensagemService } from '../../../../shared/services/mensagem.service';
import { MensagemUtil } from '../../../../shared/utils/mensagem.util';
import { Ator } from '../../models/ator.model';
import { AtorService } from '../../services/ator.service';

@Component({
	selector: 'app-ator-list',
	templateUrl: './ator-list.component.html',
	styleUrls: ['./ator-list.component.scss']
})
export class AtorListComponent {

	@BlockUI() blockUI: NgBlockUI;
	@ViewChild('table') table: Table;

	atores: Page<Ator> = new Page();
	atorSelecionado: Ator;
	filtro: Ator = new Ator();
	loader: boolean = false;

	pageListEnum = PageListEnum;
	viewAtorForm: boolean = false;
	cols = [{ header: 'Nome', field: 'nome' }];

	constructor(
		private atorService: AtorService,
		private mensagemService: MensagemService,
		private pageNotificationService: PageNotificationService
	) {
	}

	get disableEditar(): boolean {
		return !this.atorSelecionado;
	}

	get disableExcluir(): boolean {
		return !this.atorSelecionado;
	}

	buscarAtores(event?: LazyLoadEvent): void {
		const tableEvent: Table | LazyLoadEvent = !event ? this.table : event;

		this.atorSelecionado = null;
		if (this.filtro.nome) {
			this.filtrar(tableEvent);
			return;
		}
		this.buscarTodosAtores(tableEvent);
	}

	private buscarTodosAtores(tableEvent: Table | LazyLoadEvent): void {
		this.loader = true;
		this.atorService.findAll<Ator>(tableEvent)
			.pipe(finalize(() => this.loader = false))
			.subscribe(
				(res) => this.atores = res,
				(err) => this.pageNotificationService.addErrorMessage(err.message)
			);
	}

	private filtrar(tableEvent: Table | LazyLoadEvent): void {
		this.loader = true;
		this.atorService.filter<Ator>(this.filtro, tableEvent)
			.pipe(finalize(() => this.loader = false))
			.subscribe(
				(res) => this.atores = res,
				(err) => this.pageNotificationService.addErrorMessage(err.message)
			);
	}

	inserirAtor(): void {
		this.atorSelecionado = null;
		this.viewAtorForm = true;
	}

	editarAtor(): void {
		this.viewAtorForm = true;
	}

	excluirAtores(): void {
		this.mensagemService.exibirMensagem(
			'EXCLUIR ATOR(ES)',
			`Tem certeza que seja excluir o/a ator/atriz "${ this.atorSelecionado.nome }"`,
			this,
			() => this.excluir()
		);
	}

	limparFiltro(): void {
		this.filtro = new Ator();
		this.buscarAtores();
	}

	private excluir(): void {
		this.blockUI.start(MensagemUtil.BLOCKUI_EXCLUINDO);
		this.atorService.delete(this.atorSelecionado.id)
			.pipe(finalize(() => this.blockUI.stop()))
			.subscribe(
				() => {
					this.pageNotificationService.addSuccessMessage('Ator excluido com sucesso', 'Sucesso');
					this.buscarAtores();
				},
				(err) => this.pageNotificationService.addErrorMessage(err.message)
			);
	}

}
