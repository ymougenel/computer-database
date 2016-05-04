package com.excilys.database.entities;

import java.time.LocalDate;

/**
 * Computer Bean
 *
 * @author Yann Mougenel
 *
 */
public class Computer implements Entity {
    private Long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * private constructor called by builder.
     *
     * @param builder
     *            builder which contains computer attributes
     */
    private Computer(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public Computer() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        String split = "\t\t";
        String splitName = "\t\t";
        if (name != null) {
            splitName = (name.length() > 17 ? "\t" : (name.length() < 10 ? "\t\t\t" : "\t\t"));
        }

        return "id: " + this.id + "\t" + "name: " + (name == null ? "NULL" : name) + splitName
                + "introduced: " + ((introduced != null) ? introduced.toString() : "NULL") + split
                + "discontinued: " + ((discontinued != null) ? discontinued.toString() : "NULL")
                + split + "company_id: " + (company != null ? company.getName() : "null");

    }

    /**
     * Builds a computer with the suited parameters
     * (Based on the Builder pattern)
     */
    public static class Builder {
        // required parameters
        private String name;

        // optional parameters
        private Long id = null;
        private LocalDate introduced = null;
        private LocalDate discontinued = null;
        private Company company = null;

        public Builder(String name) {
            this.name = name;
        }

        public Builder introduced(LocalDate localDate) {
            this.introduced = localDate;
            return this;
        }

        public Builder discontinued(LocalDate localDate) {
            this.discontinued = localDate;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Build a new computer with prepared parameters.
         *
         * @return initialized computer
         */
        public Computer build() {
            return new Computer(this);
        }
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
