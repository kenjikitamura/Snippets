package jp.rainbowdevil.snippets.ui;

import jp.rainbowdevil.snippets.SnippetManager;
import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.sync.SynchronizeManager;

/**
 * �A�v���P�[�V�����E�C���h�E��interface
 * 
 * �eOS���Ƃ�UI���쐬����ꍇ�́A���ʂ̏������`��������interface����������B
 * 
 * @author kkitamura
 *
 */
public interface ISnippetWindow {
	/** 
	 *   �f�t�H���g�A�v���� 
	 * 
	 * ���ۂɎg�p����ꍇ�� getApplicationTitle()���g�p���邱�ƁB */
	public static String APP_NAME = "Snippets";
	
	/** �A�v���P�[�V���������擾���� */
	public String getApplicationTitle();
	
	/** �X�j�y�b�g��I����Ԃɂ���B */
	public void selectCurrentSnippet(ISnippet snippet);
	
	//public void selectCurrentSnippet

	/** �O���[�v��I����Ԃɂ���B */
	public void selectCurrentGroupItem(IGroupItem groupItem);
	
	/** �X�j�y�b�g�̏�Ԃ��ĕ`�悷��B */
	//public void notifyDataChanged();
	
	/** SnippetManager���擾����B */
	public SnippetManager getSnippetManager();
	
	public SynchronizeManager getSynchronizeManager();
	
	/** �A�v���P�[�V�������I������B */
	public void exit();
	
	/** �X�j�y�b�g�̏�Ԃ��ĕ`�悷��B */
	public void refresh();
	
	/** �E�C���h�E�^�C�g����ݒ肷��B
	 * 
	 *  �ȉ���MESSAGE�̕������w�肷��B
	 *  Snppets - MESSAGE */
	public void setWindowTitleMessage(String titleMessage);
}
