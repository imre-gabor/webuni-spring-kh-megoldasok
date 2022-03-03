package hu.webuni.eduservice.xmlws;

import javax.jws.WebService;

@WebService
public interface StudentXmlWs {

	public int getFreeSemesterByStudent(int studentId);
}
