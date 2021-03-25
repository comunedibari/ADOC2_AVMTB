package it.eng.aurigamailbusiness.database.mail;
// Generated 14-apr-2017 14.35.28 by Hibernate Tools 3.5.0.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Mailbox generated by hbm2java
 */
@Entity
@Table(name = "MAILBOX", uniqueConstraints = { @UniqueConstraint(columnNames = "NAME"), @UniqueConstraint(columnNames = { "ID_ACCOUNT", "FOLDER" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class Mailbox implements java.io.Serializable {

	private static final long serialVersionUID = -8512105036519052831L;
	private String idMailbox;
	private MailboxAccount mailboxAccount;
	private String description;
	private String name;
	private String processFlow;
	private String status;
	private String folder;
	private Set<MailboxConfig> mailboxConfigs = new HashSet<MailboxConfig>(0);
	private Set<MailboxOperation> mailboxOperations = new HashSet<MailboxOperation>(0);
	private Set<MailboxInfo> mailboxInfos = new HashSet<MailboxInfo>(0);
	private Set<MailboxMessage> mailboxMessages = new HashSet<MailboxMessage>(0);

	public Mailbox() {
	}

	public Mailbox(String idMailbox, String name, String processFlow, String status, String folder) {
		this.idMailbox = idMailbox;
		this.name = name;
		this.processFlow = processFlow;
		this.status = status;
		this.folder = folder;
	}

	public Mailbox(String idMailbox, MailboxAccount mailboxAccount, String description, String name, String processFlow, String status, String folder,
			Set<MailboxConfig> mailboxConfigs, Set<MailboxOperation> mailboxOperations, Set<MailboxInfo> mailboxInfos, Set<MailboxMessage> mailboxMessages) {
		this.idMailbox = idMailbox;
		this.mailboxAccount = mailboxAccount;
		this.description = description;
		this.name = name;
		this.processFlow = processFlow;
		this.status = status;
		this.folder = folder;
		this.mailboxConfigs = mailboxConfigs;
		this.mailboxOperations = mailboxOperations;
		this.mailboxInfos = mailboxInfos;
		this.mailboxMessages = mailboxMessages;
	}

	@Id

	@Column(name = "ID_MAILBOX", unique = true, nullable = false, length = 40)
	public String getIdMailbox() {
		return this.idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ACCOUNT")
	public MailboxAccount getMailboxAccount() {
		return this.mailboxAccount;
	}

	public void setMailboxAccount(MailboxAccount mailboxAccount) {
		this.mailboxAccount = mailboxAccount;
	}

	@Column(name = "DESCRIPTION", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PROCESS_FLOW", nullable = false)
	public String getProcessFlow() {
		return this.processFlow;
	}

	public void setProcessFlow(String processFlow) {
		this.processFlow = processFlow;
	}

	@Column(name = "STATUS", nullable = false, length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "FOLDER", nullable = false, length = 50)
	public String getFolder() {
		return this.folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mailbox")
	public Set<MailboxConfig> getMailboxConfigs() {
		return this.mailboxConfigs;
	}

	public void setMailboxConfigs(Set<MailboxConfig> mailboxConfigs) {
		this.mailboxConfigs = mailboxConfigs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mailbox")
	public Set<MailboxOperation> getMailboxOperations() {
		return this.mailboxOperations;
	}

	public void setMailboxOperations(Set<MailboxOperation> mailboxOperations) {
		this.mailboxOperations = mailboxOperations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mailbox")
	public Set<MailboxInfo> getMailboxInfos() {
		return this.mailboxInfos;
	}

	public void setMailboxInfos(Set<MailboxInfo> mailboxInfos) {
		this.mailboxInfos = mailboxInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mailbox")
	public Set<MailboxMessage> getMailboxMessages() {
		return this.mailboxMessages;
	}

	public void setMailboxMessages(Set<MailboxMessage> mailboxMessages) {
		this.mailboxMessages = mailboxMessages;
	}

}
