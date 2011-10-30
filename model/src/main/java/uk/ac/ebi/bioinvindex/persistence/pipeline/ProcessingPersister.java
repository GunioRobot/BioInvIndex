/*
 * __________
 * CREDITS
 * __________
 *
 * Team page: http://isatab.sf.net/
 * - Marco Brandizi (software engineer: ISAvalidator, ISAconverter, BII data management utility, BII model)
 * - Eamonn Maguire (software engineer: ISAcreator, ISAcreator configurator, ISAvalidator, ISAconverter,  BII data management utility, BII web)
 * - Nataliya Sklyar (software engineer: BII web application, BII model,  BII data management utility)
 * - Philippe Rocca-Serra (technical coordinator: user requirements and standards compliance for ISA software, ISA-tab format specification, BII model, ISAcreator wizard, ontology)
 * - Susanna-Assunta Sansone (coordinator: ISA infrastructure design, standards compliance, ISA-tab format specification, BII model, funds raising)
 *
 * Contributors:
 * - Manon Delahaye (ISA team trainee:  BII web services)
 * - Richard Evans (ISA team trainee: rISAtab)
 *
 *
 * ______________________
 * Contacts and Feedback:
 * ______________________
 *
 * Project overview: http://isatab.sourceforge.net/
 *
 * To follow general discussion: isatab-devel@list.sourceforge.net
 * To contact the developers: isatools@googlegroups.com
 *
 * To report bugs: http://sourceforge.net/tracker/?group_id=215183&atid=1032649
 * To request enhancements:  http://sourceforge.net/tracker/?group_id=215183&atid=1032652
 *
 *
 * __________
 * License:
 * __________
 *
 * This work is licenced under the Creative Commons Attribution-Share Alike 2.0 UK: England & Wales License. To view a copy of this licence, visit http://creativecommons.org/licenses/by-sa/2.0/uk/ or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California 94105, USA.
 *
 * __________
 * Sponsors
 * __________
 * This work has been funded mainly by the EU Carcinogenomics (http://www.carcinogenomics.eu) [PL 037712] and in part by the
 * EU NuGO [NoE 503630](http://www.nugo.org/everyone) projects and in part by EMBL-EBI.
 */

package uk.ac.ebi.bioinvindex.persistence.pipeline;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

import uk.ac.ebi.bioinvindex.dao.ejb3.DaoFactory;
import uk.ac.ebi.bioinvindex.model.processing.Processing;
import uk.ac.ebi.bioinvindex.model.processing.ProtocolApplication;

@SuppressWarnings("unchecked")
public abstract class ProcessingPersister<P extends Processing> extends GraphElementPersister<P>
{
	private final ProtocolApplicationPersister pappPersister;

	public ProcessingPersister ( DaoFactory daoFactory, Timestamp submissionTs )
	{
		super ( daoFactory, submissionTs );
		pappPersister = new ProtocolApplicationPersister ( daoFactory, submissionTs );
	}

	@Override
	protected void preProcess ( P proc )
	{
		super.preProcess ( proc );

		// All The Proto Apps
		Collection<ProtocolApplication> papps = new LinkedList<ProtocolApplication> ( proc.getProtocolApplications () );
		for ( ProtocolApplication papp: papps )
			pappPersister.persist ( papp );

		/* TODO: remove, automatically removed by the processings removal in NodePersister
		 * will be saved by the AssayMaterialPersister
		Collection<Node> inputs = new ArrayList<Node> ( proc.getInputNodes () );
		for ( Node node: inputs ) proc.removeInputNode ( node );
		Collection<Node> outputs = new ArrayList<Node> ( proc.getOutputNodes () );
		for ( Node node: outputs ) proc.removeOutputNode ( node );
		*/
	}

}
