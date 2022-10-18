import { Ator } from '../../ator/models/ator.model';
import { Classe } from '../../classe/models/classe.model';
import { CategoriaEnum } from '../enums/categoria.enum';

export class Titulo {
	id?: number;
	nome?: string = '';
	sinopse?: string = '';
	ano?: number;
	categoria?: CategoriaEnum;
	classe?: Classe;
	atores?: Ator[] = [];

	atoresNomes?: string;
}